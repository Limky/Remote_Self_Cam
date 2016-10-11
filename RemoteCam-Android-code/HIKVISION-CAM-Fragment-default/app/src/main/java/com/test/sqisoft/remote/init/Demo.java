package com.test.sqisoft.remote.init;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by SQISOFT on 2016-10-11.
 */
public class Demo extends Application{
    public static final String USER_ID = "aaa";

    private static Context mContext = null;
    public Demo(){
        Log.d("test", "aaaaaa");
     //   mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
