package com.sqisoft.remote.domain;

/**
 * Created by SQISOFT on 2016-10-25.
 */
public class ServerImageDomain {

    private String imageTitle;
    private String imageDate;
    private String recommendation;
    private String deviceNumber;
    private String imageUrl;
    private String thumbnailUrl;

    public ServerImageDomain(String imageTitle, String imageDate, String recommendation, String deviceNumber, String imageUrl, String thumbnailUrl) {
        this.imageTitle = imageTitle;
        this.imageDate = imageDate;
        this.recommendation = recommendation;
        this.deviceNumber = deviceNumber;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDate() {
        return imageDate;
    }

    public void setImageDate(String imageDate) {
        this.imageDate = imageDate;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
