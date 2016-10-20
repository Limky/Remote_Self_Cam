package com.sqisoft.remote.domain;

import android.graphics.Bitmap;

/**
 * Created by SQISOFT on 2016-10-20.
 */
public class MyImageObject {
    private String mImageTitle;
    private String mSelfieZone;
    private Boolean mVisibility;
    private Bitmap mBitmap;

    public MyImageObject(String mSelfieZone, String mImageTitle, Bitmap mBitmap, Boolean mVisibility) {
        this.mImageTitle = mImageTitle;
        this.mBitmap = mBitmap;
        this.mVisibility = mVisibility;
        this.mSelfieZone = mSelfieZone;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getmImageTitle() {
        return mImageTitle;
    }

    public void setmImageTitle(String mImageTitle) {
        this.mImageTitle = mImageTitle;
    }

    public Boolean getmVisibility() {
        return mVisibility;
    }

    public void setmVisibility(Boolean mVisibility) {
        this.mVisibility = mVisibility;
    }

    public String getmSelfieZone() {
        return mSelfieZone;
    }

    public void setmSelfieZone(String mSelfieZone) {
        this.mSelfieZone = mSelfieZone;
    }


}
