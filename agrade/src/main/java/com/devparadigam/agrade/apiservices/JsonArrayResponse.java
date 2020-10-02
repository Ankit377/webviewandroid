package com.devparadigam.agrade.apiservices;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kipl104 on 04-Apr-17.
 */

public class JsonArrayResponse<T>  extends CommonResponser {

    @SerializedName("data")
    public List<T> list;
}
