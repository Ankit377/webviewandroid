
package com.devparadigam.agrade.model.response.youtube;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails extends BaseModel {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoPublishedAt")
    @Expose
    private String videoPublishedAt;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoPublishedAt() {
        return videoPublishedAt;
    }

    public void setVideoPublishedAt(String videoPublishedAt) {
        this.videoPublishedAt = videoPublishedAt;
    }

}

