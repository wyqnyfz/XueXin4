package com.coolweather.xuexin3.socket;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "ChatActivity";
    private Context mContext;
    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private EditText et_content;
    private ListView listView;
    private Button btn_send;
    private TextView tvContactName;
    private AppCompatImageButton btnBack;
    private AppCompatImageButton btnVoiceOrText;
    private AppCompatImageButton btnFace;  //表情
    private AppCompatImageButton btnMultimedia;  //更多功能
    private List<ChatMessage> chatMessageList = new ArrayList<>();//消息列表
    private ChatMessageAdapter chatMessageAdapter;
    private ChatMessageReceiver chatMessageReceiver;

    public int contactId;  //正在聊天的好友的ID
    public String contactName;  //正在聊天的好友名
    public String contactHeadPortrait;  //好友头像路径
    public String myHeadPortrait;  //我的头像路径


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            client = jWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "服务与活动成功断开");
        }
    };

    private class ChatMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            String data = "";

            try {
                data = new JSONObject(message).getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ChatMessage chatMessage = new Gson().fromJson(data, ChatMessage.class);

            chatMessageList.add(chatMessage);
            initChatMsgListView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext=ChatActivity.this;
//隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        contactId = getIntent().getIntExtra("friend_id", -1);
        contactHeadPortrait = getIntent().getStringExtra("friend_img");
        contactName = getIntent().getStringExtra("friend_name");
        myHeadPortrait = MyData.sBasicData.photo;

        Log.i(TAG, "onCreate: friendInfo[id="+contactId+", name="+contactName+", img="+contactHeadPortrait+", myImg="+myHeadPortrait+"]");


        startJWebSClientService();  //启动服务
        bindService();  //绑定服务
        doRegisterReceiver();  //注册广播

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                register();
            }
        }).start();
    }

    /**
     * 注册信道，告知服务器用户已在线，可以接收聊天信息
     */
    public void register(){
        RegisterBean bean = new RegisterBean();
        bean.setFromUserId(MyData.sBasicData.id);
        bean.setType("REGISTER");

        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(now);
        bean.setDateTime(strDate);

        String msg = new Gson().toJson(bean);
        Log.i(TAG, "register: "+msg);
        jWebSClientService.sendMsg(msg);
    }

    static class RegisterBean{
        private int fromUserId;
        private String type;
        private String dateTime;

        public int getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(int fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(mContext, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 启动服务（WebSocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(mContext, JWebSocketClientService.class);
        startService(intent);
    }

    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(chatMessageReceiver, filter);
    }

    private void initView() {
        listView = findViewById(R.id.chatmsg_listView);
        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.et_content);
        btnFace = findViewById(R.id.btn_face);
        btnMultimedia = findViewById(R.id.btn_multimedia);
        btnVoiceOrText = findViewById(R.id.btn_voice_or_text);
        tvContactName = findViewById(R.id.tv_groupOrContactName);
        btnBack = findViewById(R.id.iv_return);

        btnVoiceOrText.setOnClickListener(this);
        btnFace.setOnClickListener(this);
        btnMultimedia.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //监听输入框的变化
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_content.getText().toString().length() > 0) {
                    btn_send.setVisibility(View.VISIBLE);
                    btnFace.setVisibility(View.GONE);
                    btnMultimedia.setVisibility(View.GONE);
                } else {
                    btn_send.setVisibility(View.GONE);
                    btnFace.setVisibility(View.VISIBLE);
                    btnMultimedia.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if(contactName!=null){
            tvContactName.setText(contactName);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:{
                String content = et_content.getText().toString();
                if (content.length() <= 0) {
                    WSUtil.showToast(mContext, "消息不能为空哟");
                    return;
                }

                if (client != null && client.isOpen()) {

                    //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
                    ChatMessage chatMessage=new ChatMessage();
                    chatMessage.setContent(content);
                    chatMessage.setFromUserId(new Integer(MyData.sBasicData.id));
                    chatMessage.setToUserId(new Integer(contactId));
                    chatMessage.setDateTime(System.currentTimeMillis()+"");
                    chatMessage.setType(WSUtil.MSG_TYPE);


                    String msg = new Gson().toJson(chatMessage);
                    jWebSClientService.sendMsg(msg);

                    chatMessageList.add(chatMessage);
                    initChatMsgListView();
                    et_content.setText("");

                } else {
                    WSUtil.showToast(mContext, "连接已断开，请稍等或重启App哟");
                }
                break;
            }

            case R.id.btn_face:{
                WSUtil.showToast(mContext, "表情功能尚未开放！");
                break;
            }

            case R.id.btn_voice_or_text:{
                WSUtil.showToast(mContext, "语音功能尚未开放！");
                break;
            }

            case R.id.btn_multimedia:{
                WSUtil.showToast(mContext, "此功能尚未开放！");
                break;
            }

            case R.id.iv_return:{
                finish();
                break;
            }

            default:
                break;
        }
    }

    private void initChatMsgListView(){
        chatMessageAdapter = new ChatMessageAdapter(mContext, chatMessageList, contactHeadPortrait, myHeadPortrait);
        listView.setAdapter(chatMessageAdapter);
        listView.setSelection(chatMessageList.size());
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        unregisterReceiver(chatMessageReceiver);
        super.onDestroy();
    }


}
