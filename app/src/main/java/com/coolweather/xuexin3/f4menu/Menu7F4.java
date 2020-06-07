package com.coolweather.xuexin3.f4menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.startactiity.Login;


public class Menu7F4 extends AppCompatActivity {

    private String TAG = "Menu7F4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu7_f4);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!MyData.isLogin){
            Log.d(TAG, "销毁");
            finish();
        }
    }

    private void init() {



        //修改密码
        findViewById(R.id.tv_m7f4_changpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu7F4.this,ChangePassword.class));
            }
        });


        //删除
        findViewById(R.id.tv_m7f4_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Menu7F4.this, "退出成功！", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                editor.apply();

                Intent intent = new Intent(Menu7F4.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
