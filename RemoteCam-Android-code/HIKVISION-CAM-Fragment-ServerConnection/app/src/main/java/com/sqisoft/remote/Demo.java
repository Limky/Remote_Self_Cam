package com.sqisoft.remote;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by SQISOFT on 2016-10-11.
 */
public class Demo extends Application {
    public static final boolean MODE_TEST = true;

    public static String SERVER_URL = "http://";


    public static final String TEMP_SERVER_URL = "220.76.251.180";
    public static final String TEMP_SERVER_CAPTURE_URL ="http://"+TEMP_SERVER_URL+"/Streaming/channels/3/picture";
    public static final int TEMP_SERVER_PORT = 8000;
    public static final String USER_ID = "admin";
    public static final String PASSWORD = "1234qwer";


    private static Context mContext = null;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }


}


