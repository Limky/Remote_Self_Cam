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
    public static final String USER_ID = "aaa";

    private static Context mContext = null;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }


}


