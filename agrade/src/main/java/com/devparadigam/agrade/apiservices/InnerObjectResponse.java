package com.devparadigam.agrade.apiservices;

import com.google.gson.annotations.SerializedName;

public class InnerObjectResponse<T> {
    @SerializedName("paper")
    public  T paper;

    public T getPaper() {
        return paper;
    }
}
