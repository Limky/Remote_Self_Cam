package com.sqisoft.remote.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.domain.ServerImageObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-11.
 */
public class DataManager {
    private static DataManager instance = null;

    public Bitmap bitmap = null;
    public ArrayList<ServerImageObject> serverImageObjects = new ArrayList<ServerImageObject>();
    public ArrayList<ServerImageDomain> serverImageDomains = new ArrayList<ServerImageDomain>();
    public ArrayList<LocalImageObject> localImageObjects = new ArrayList<LocalImageObject>();

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

    public ArrayList<ServerImageObject> getServerImageObjects() {
        return serverImageObjects;
    }
    public void setServerImageObjects(ArrayList<ServerImageObject> serverImageObjects) {
        this.serverImageObjects = serverImageObjects;
    }

    public ArrayList<LocalImageObject> getLocalImageObjects() {
        return localImageObjects;
    }

    public void setLocalImageObjects(ArrayList<LocalImageObject> localImageObjects) {
        this.localImageObjects = localImageObjects;
    }

    public ArrayList<ServerImageDomain> getServerImageDomains() {
        return serverImageDomains;
    }

    public void setServerImageDomains(ArrayList<ServerImageDomain> serverImageDomains) {
        this.serverImageDomains = serverImageDomains;
    }

    public void addServerImageDomain(ServerImageDomain serverImageDomain){
        this.serverImageDomains.add(serverImageDomain);
    }

    public Bitmap getWebimage(String URL){
        Bitmap webBitmap = null;
        try {
            //웹사이트에 접속 (사진이 있는 주소로 접근)
            java.net.URL Url = new URL(URL);
            // 웹사이트에 접속 설정
            HttpURLConnection urlcon =(HttpURLConnection) Url.openConnection();
            //    Log.i("urlcon","Web-CODE ========1========= "+urlcon.getResponseCode());

            urlcon.connect();
            //     Log.i("urlcon","Web-CODE ========2========= "+urlcon.getResponseCode());
            // 이미지 길이 불러옴
            int imagelength = urlcon.getContentLength();
            // 스트림 클래스를 이용하여 이미지를 불러옴
            BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
            // 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
            webBitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            return webBitmap;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return webBitmap;
    }


}


