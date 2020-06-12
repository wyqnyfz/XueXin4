package com.coolweather.xuexin3.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;


/**
 * 在本项目中，客户端在成功连接服务器后，还要向服务器发送一条注册信息，表明用户已在线。
 * 然后才能从服务器接收聊天信息
 */
public class JWebSocketClientService extends Service {
    public JWebSocketClient client;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();

    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            return JWebSocketClientService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //初始化webSocket
        initSocketClient();
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        closeConnect();
        super.onDestroy();
    }

    public JWebSocketClientService() {
    }

    /**
     * 初始化webSocket连接
     */
    private void initSocketClient() {
        URI uri = URI.create(WSUtil.ws);
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                Log.d("JWebSocketClientService", "收到的消息：" + message);

                String data = "";
                String type = "";
                try {
                    data = new JSONObject(message).getString("data");
                    type = new JSONObject(data).getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(!type.equals("REGISTER")){  //如果接收到的不是注册信道时的返回信息，即接收到的信息为用户聊天信息
                    Intent intent = new Intent();
                    intent.setAction("com.xch.servicecallback.content");
                    intent.putExtra("message", message);
                    sendBroadcast(intent);
                }

            }

            @Override
            public void onOpen(ServerHandshake handshakeData) {
                super.onOpen(handshakeData);
                Log.d("JWebSocketClientService", "webSocket连接成功");
            }
        };

        connect();
    }

    /**
     * 连接webSocket
     */
    private void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != client) {
            Log.d("JWebSocketClientService", "发送的消息：" + msg);
            client.send(msg);
        }
    }

    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }


    //    -------------------------------------WebSocket心跳检测------------------------------------------------
    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i("JWebSocketClientService", "心跳包检测WebSocket连接状态");
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null;
                initSocketClient();
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
