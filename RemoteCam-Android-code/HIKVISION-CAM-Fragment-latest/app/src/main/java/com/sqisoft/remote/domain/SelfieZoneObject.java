package com.sqisoft.remote.domain;

import android.graphics.Bitmap;

/**
 * Created by SQISOFT on 2016-10-20.
 */
public class SelfieZoneObject {

    private String mTitle;
    private String mDesc;
    private Bitmap mTourBitmap;

    public SelfieZoneObject(String mTitle, String mDesc, Bitmap mTourBitmap) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mTourBitmap = mTourBitmap;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public Bitmap getmTourBitmap() {
        return mTourBitmap;
    }

    public void setmTourBitmap(Bitmap mTourBitmap) {
        this.mTourBitmap = mTourBitmap;
    }

}
