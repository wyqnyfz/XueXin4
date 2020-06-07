package com.coolweather.xuexin3.startactiity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.MainActivity;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;

public class Login extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private Button mLogin;
    private TextView mRegister;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();

                        //data是数据表的名字
                        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                        boolean b = sharedPreferences.getBoolean("isLogin", false);
                        if(!b){
                            //存入
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", mName.getText().toString());
                            editor.putString("password", mPassword.getText().toString());
                            editor.putBoolean("isLogin", true);
                            editor.apply();
                        }

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //data是数据表的名字

        init();
    }

    private void init() {
        mName = findViewById(R.id.et_login_name);
        mPassword = findViewById(R.id.et_login_password);
        mLogin = findViewById(R.id.bt_login_login);
        mRegister = findViewById(R.id.tv_login_register);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Message m = new Message();
//                m.what =1;
//                m.obj = 1;
//                mHandler.sendMessage(m);


                String s1 = mName.getText().toString();
                String s2 = mPassword.getText().toString();
                if("".equals(s1) || "".equals(s2)){
                    Toast.makeText(Login.this, "账户或密码不能为空!", Toast.LENGTH_SHORT).show();
                }else{
                    MyUtils.name = s1;
                    MyUtils.password = s2;
                    loging();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Login.this,Register.class));
            }
        });

    }

    /**
     * 登录
     */
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
