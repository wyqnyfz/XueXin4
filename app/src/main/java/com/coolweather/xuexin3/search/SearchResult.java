package com.coolweather.xuexin3.search;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.R;


public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();

    }

    private void init() {
        Intent intent = getIntent();
        String s = intent.getStringExtra("search");
        TextView t =findViewById(R.id.tv_s2);
        t.setText("抱歉！您搜索的"+s+"未找到");
    }


}
