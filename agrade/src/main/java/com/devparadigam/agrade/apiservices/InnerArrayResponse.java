package com.devparadigam.agrade.apiservices;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InnerArrayResponse<T> {

    @SerializedName("examination")
    public List<T> examinatioList;

    @SerializedName("paper")
    public List<T> paperList;
}
