package com.devparadigam.agrade.model.response;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestHistoryModel extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("paper_id")
    @Expose
    private String paperId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("marks")
    @Expose
    private String marks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

}
