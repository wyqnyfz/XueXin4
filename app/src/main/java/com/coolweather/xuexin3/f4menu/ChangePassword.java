package com.coolweather.xuexin3.f4menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;

public class ChangePassword extends AppCompatActivity {

    private static final String TAG = "ChangePassword";
    private EditText mPass0;
    private EditText mPass1;
    private EditText mPass2;


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
                        Toast.makeText(ChangePassword.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        MyData.isLogin = false;
                        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", mPass1.getText().toString());
                        editor.apply();
                        finish();
                    }else {
                        Toast.makeText(ChangePassword.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        init();
    }

    private void init() {
        mPass0 = findViewById(R.id.et_cp_pass0);
        mPass1 = findViewById(R.id.et_cp_pass1);
        mPass2 = findViewById(R.id.et_cp_pass2);
        findViewById(R.id.bt_cp_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s0 = mPass0.getText().toString();
                String s1 = mPass1.getText().toString();
                String s2 = mPass2.getText().toString();
                if(s1.equals(s2)){
                    MyUtils.pass0 = s0;
                    MyUtils.pass1 = s1;
                    MyUtils.phone = MyUtils.name;
                    change();
                }else {
                    Toast.makeText(ChangePassword.this, "新密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void change() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.post_10();
            }
        }).start();
    }


}
