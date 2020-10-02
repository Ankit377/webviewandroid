package com.devparadigam.agrade.model.response;


import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamSelectionModel extends BaseModel{

    @SerializedName("image")
    @Expose
    private int image;
    @SerializedName("image")
    @Expose
    private String title;

    public ExamSelectionModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
