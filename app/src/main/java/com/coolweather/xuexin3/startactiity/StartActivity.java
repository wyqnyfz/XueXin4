package com.coolweather.xuexin3.startactiity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.MainActivity;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class StartActivity extends AppCompatActivity {


    private static final String TAG = "StartActivity";

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE};

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(StartActivity.this, "账号异常", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        getPermission();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);

                    SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                    boolean b = sharedPreferences.getBoolean("isLogin", false);
                    if(!b){
                        Intent intent = new Intent(StartActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }else {
                        MyUtils.name = sharedPreferences.getString("name", "");
                        MyUtils.password = sharedPreferences.getString("password", "");
                        Log.d(TAG, MyUtils.name);
                        Log.d(TAG, MyUtils.password);
                        loging();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 66) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this,"权限申请成功",Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this,"权限被拒绝",Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    /**
     * 获取权限
     */
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //成功打开权限
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    public void onPermissionsDenied(int requestCode,  List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }


    private void loging() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.post_2();
            }
        }).start();
    }

}
