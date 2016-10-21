package com.sqisoft.remote.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SQISOFT on 2016-10-20.
 */
public class LocalImageObject implements Parcelable{
    private String mImageTitle;
    private String mSelfieZone;
    private Boolean mVisibility;
    private Bitmap mBitmap;
    private String mImagePath;

    public LocalImageObject(String mImageTitle, String mSelfieZone, String mImagePath, Boolean mVisibility) {
        this.mImageTitle = mImageTitle;
        this.mSelfieZone = mSelfieZone;
        this.mVisibility = mVisibility;
        this.mImagePath = mImagePath;
    }

    public LocalImageObject(String mSelfieZone, String mImageTitle, Bitmap mBitmap, Boolean mVisibility) {
        this.mImageTitle = mImageTitle;
        this.mBitmap = mBitmap;
        this.mVisibility = mVisibility;
        this.mSelfieZone = mSelfieZone;
    }


    protected LocalImageObject(Parcel in) {
        mImagePath = in.readString();
    }

    public static final Parcelable.Creator<LocalImageObject> CREATOR = new Parcelable.Creator<LocalImageObject>() {
        @Override
        public LocalImageObject createFromParcel(Parcel in) {
            return new LocalImageObject(in);
        }

        @Override
        public LocalImageObject[] newArray(int size) {
            return new LocalImageObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImagePath);
    }
    
    

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
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
