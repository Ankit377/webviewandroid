package com.devparadigam.agrade.apiservices;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kipl104 on 04-Apr-17.
 */

public class JsonObjectResponse<T> extends CommonResponser {

    @SerializedName("data")
    public T data;


    public T getData() {
        return data;
    }
}
