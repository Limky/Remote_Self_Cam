package com.sqisoft.remote.domain;

/**
 * Created by SQISOFT on 2016-10-25.
 */
public class ServerImageDomain {
    private String imageTitle;
    private String imageDesc;
    private String imageUrl;

    @Override
    public String toString() {
        return "ServerImageDomain{" +
                "imageTitle='" + imageTitle + '\'' +
                ", imageDesc='" + imageDesc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }


    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }




}
