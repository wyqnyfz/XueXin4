package com.coolweather.xuexin3.startactiity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;

public class Register extends AppCompatActivity {
    private String TAG = "Register";

    private EditText mName;
    private EditText mPassword1;
    private EditText mPassword2;
    private EditText mCode;
    private Button mGetCode;
    private Button mReg;

    private boolean b1 = false;
    private boolean b2 = false;

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t==1){
                        Toast.makeText(Register.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                    }else if(t==0){
                        Toast.makeText(Register.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Register.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int tt = (int) msg.obj;
                    if(tt==1){
                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Register.this, "该手机号码已经被注册", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        mName = findViewById(R.id.et_reg_name);
        mPassword1 = findViewById(R.id.et_reg_password1);
        mPassword2 = findViewById(R.id.et_reg_password2);
        mCode = findViewById(R.id.et_reg_code);
        mGetCode = findViewById(R.id.bt_reg_getcode);
        mReg = findViewById(R.id.bt_reg_reg);
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String pass1 = mPassword1.getText().toString();
                String pass2 = mPassword2.getText().toString();
                if("".equals(name)||"".equals(pass1)||"".equals(pass2)){
                    Toast.makeText(Register.this, "以上不能为空！", Toast.LENGTH_SHORT).show();
                }else if(!pass1.equals(pass2)){
                    Toast.makeText(Register.this, "两次密码不一样！", Toast.LENGTH_SHORT).show();
                }else{
                    sentYanZheng(name);
                }
            }
        });

        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String pass1 = mPassword1.getText().toString();
                String pass2 = mPassword2.getText().toString();
                String yanzheng = mCode.getText().toString();
                if("".equals(name)||"".equals(pass1)||"".equals(pass2)){
                    Toast.makeText(Register.this, "以上不能为空！", Toast.LENGTH_SHORT).show();
                }else if(!pass1.equals(pass2)){
                    Toast.makeText(Register.this, "两次密码不一样！", Toast.LENGTH_SHORT).show();
                }else{
                    MyUtils.name = name;
                    MyUtils.password = pass1;
                    Log.d(TAG, "---------------yanzheng"+yanzheng);
                    if(yanzheng.equals(MyUtils.zhucema)){
                        addAccount();
                    }else{
                        Toast.makeText(Register.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 验证码
     */
    private void sentYanZheng(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!b1){
                    b1 = true;
                    MyUtils.setHandler(mHandler);
                    MyUtils.get_1(name);
                    b1 = false;
                }

            }
        }).start();

    }

    /**
     * 注册
     */
    private void addAccount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!b2){
                    b2 = true;
                    MyUtils.setHandler(mHandler);
                    MyUtils.post_1();
                    b2 = false;
                }

            }
        }).start();
    }




}
