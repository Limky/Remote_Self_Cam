package com.sqisoft.remote.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetUtil {
    public static String readAssets(Context context, String url){
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open(url), "UTF-8")); 

            String mLine = reader.readLine();
            
            while (mLine != null) {
               sb.append(mLine);
               mLine = reader.readLine(); 
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            if (reader != null) {
                 try {
                     reader.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
            }
        }
        return sb.toString();
    }
}
