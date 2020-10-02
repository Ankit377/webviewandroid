package com.devparadigam.agrade.model.response;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetailsModel extends BaseModel {

    @SerializedName("VideoName")
    @Expose
    private String VideoName;
    @SerializedName("VideoDesc")
    @Expose
    private Integer VideoDesc;
    @SerializedName("URL")
    @Expose
    private String URL;
    @SerializedName("VideoId")
    @Expose
    private String VideoId;

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public Integer getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoDesc(Integer videoDesc) {
        VideoDesc = videoDesc;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }
}
