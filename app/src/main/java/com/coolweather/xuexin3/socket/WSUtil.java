package com.coolweather.xuexin3.socket;

import android.content.Context;
import android.widget.Toast;

public class WSUtil {
    public static final String ws = "ws://121.36.5.207:8000";//webSocket测试地址
    public static final String MSG_TYPE = "SINGLE_SENDING";

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }


}
