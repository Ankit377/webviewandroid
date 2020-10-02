package com.devparadigam.agrade.model.response;


import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueNoModel extends BaseModel{

    @SerializedName("number")
    @Expose
    private int number;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
