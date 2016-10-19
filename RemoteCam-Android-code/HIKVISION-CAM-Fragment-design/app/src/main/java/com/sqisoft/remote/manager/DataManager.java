package com.sqisoft.remote.manager;

import android.graphics.Bitmap;

/**
 * Created by SQISOFT on 2016-10-11.
 */
public class DataManager {
    private static DataManager instance = null;

    public Bitmap bitmap = null;

    private DataManager(){

    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public int getAaa() {
        return aaa;
    }

    public void setAaa(int aaa) {
        this.aaa = aaa;
    }

    private int aaa = 1;


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap (){
        return  this.bitmap;
    }

}


