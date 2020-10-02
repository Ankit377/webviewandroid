package com.devparadigam.agrade.utils;

import android.Manifest;

import com.devparadigam.agrade.BuildConfig;

/**
 * Created by kipl146 on 6/2/2017.
 */

public class StaticData {

    public static boolean DEBUG = true;

    public static final String HEADER_APP_VERSION = BuildConfig.VERSION_NAME;
    public static final String HEADER_LANGUAGE = "en";
    public static final String HEADER_DEVICE_OS = "android";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImUxN2IzYzQzYzI3MjEwNDQxYzhjYTViMjBmNTQzNzk1YzZjYzdhNmNjNjY4YWNmNWU4MzQ4NjhkZmZjNDlkMjMxNmM0YjViNzYxNTRlZjBjIn0.eyJhdWQiOiIxMiIsImp0aSI6ImUxN2IzYzQzYzI3MjEwNDQxYzhjYTViMjBmNTQzNzk1YzZjYzdhNmNjNjY4YWNmNWU4MzQ4NjhkZmZjNDlkMjMxNmM0YjViNzYxNTRlZjBjIiwiaWF0IjoxNTc3MDk0NzM2LCJuYmYiOjE1NzcwOTQ3MzYsImV4cCI6MTYwODcxNzEzNiwic3ViIjoiNDUiLCJzY29wZXMiOltdfQ.WI8ZtfyuM1SABGHSLKziYKy-OHuDiVJ6uS4f3j5J68p6lrfnFP6jvgAovyFRaTW-s1GKng1Mp0YNfDcSEAL12DC9Mj9o1T4Pthtc9Gss9n5zhlPXFdxAyPXTvheUngt7yveLYfZ2NALqvDrOeV7tq4P-bHXqqqqEinVYJcHDJdMPGfYVxGKtvGcuUSAxyeCrtO8f7t6xIyWTQe00I857ul2oR91SWEUWrM8NV5krvULNAKYYo1BVJELNZSAvDk3FYdKxhPn9E-Lor3J-xfobOc2Q1ptL7B2q7eG-COOelp-xtKVdJA5yDMFuq7GTJwDMOv0tPGOFifLUpfOrN01Del9ObJvdo8YscagRTnLJ22Zy4MuHqyzgFLBC40F_9cyZ-6k-aErXDaJewCukQiuVm5XP2cSvS9u8tEdHyDMvsIMrtRz6uBxJNFi5yCLHspjJKrj-VVpcYexhZURqwD4-QjAHd1gMiRxB1_L8NOwhmICLDFBbF3d44ek5SHyNduskYq9W21DAIOtJliuJAadXCmQk0C3Xu3QBl8egTDpzrBoS6tIszfhYYPAk-CvIkU-c_EL1omLtsWx4M2tDouqfRi5Aq4ZJmxZF5hWJF7atZJ9fHgrUtlokS9h1zbNZ5zxSh78Ao5WnxSL8iYozwA4S7lSV9SYGvVQUDLQp99ZOYXc";
   // public static final String BASE_URL = "http:/.130.122.new.ocpwebserver.com/";
  // public static final String BASE_URL = "http://192.168.43.172:8001/";
//   public static final String BASE_URL = "http://ec2-52-15-178-246.us-east-2.compute.amazonaws.com/";
   public static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/";
   public static final String BASE_URL = "\n" + "http://revolutionsoftwareservices.com/api/rest/";
    public static final int PERMISSIONS_REQUEST_LOCATION = 16;
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};



}
