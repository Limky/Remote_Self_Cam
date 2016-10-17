package com.test.sqisoft.remote.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SQISOFT on 2016-10-07.
 */
public class ImageObject implements Parcelable {

    private String imagePath;

    public ImageObject(String imagePath) {

        this.imagePath = imagePath;
    }

    protected ImageObject(Parcel in) {
        imagePath = in.readString();
    }

    public static final Creator<ImageObject> CREATOR = new Creator<ImageObject>() {
        @Override
        public ImageObject createFromParcel(Parcel in) {
            return new ImageObject(in);
        }

        @Override
        public ImageObject[] newArray(int size) {
            return new ImageObject[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);

    }

}