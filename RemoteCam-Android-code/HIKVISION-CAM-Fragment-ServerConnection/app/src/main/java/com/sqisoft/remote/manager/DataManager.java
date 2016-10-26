package com.sqisoft.remote.manager;

import android.graphics.Bitmap;

import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.domain.ServerImageDomain;
import com.sqisoft.remote.domain.ServerImageObject;

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

}


