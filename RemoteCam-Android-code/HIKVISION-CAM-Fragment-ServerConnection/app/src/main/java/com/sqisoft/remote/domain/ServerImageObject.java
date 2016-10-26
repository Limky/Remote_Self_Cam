package com.sqisoft.remote.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SQISOFT on 2016-10-07.
 */
public class ServerImageObject implements Parcelable {

    private String mSelfieZone;
    private String mImageTitle;
    private Bitmap mBitmap;
    private String mImagePath;

    public ServerImageObject(String mImagePath) {

        this.mImagePath = mImagePath;
    }
    public ServerImageObject(String mImagePath,String imageTitle) {

        this.mImagePath = mImagePath;
        this.mImageTitle = imageTitle;
    }

    protected ServerImageObject(Parcel in) {
        mImagePath = in.readString();
    }

    public static final Creator<ServerImageObject> CREATOR = new Creator<ServerImageObject>() {
        @Override
        public ServerImageObject createFromParcel(Parcel in) {
            return new ServerImageObject(in);
        }

        @Override
        public ServerImageObject[] newArray(int size) {
            return new ServerImageObject[size];
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

    public String getmSelfieZone() {
        return mSelfieZone;
    }

    public void setmSelfieZone(String mSelfieZone) {
        this.mSelfieZone = mSelfieZone;
    }

    public String getmImageTitle() {
        return mImageTitle;
    }

    public void setmImageTitle(String mImageTitle) {
        this.mImageTitle = mImageTitle;
    }

}