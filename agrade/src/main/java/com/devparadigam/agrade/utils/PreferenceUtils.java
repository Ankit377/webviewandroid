package com.devparadigam.agrade.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.google.gson.Gson;



/**
 * Created by kipl146 on 4/13/2016.
 */
public class PreferenceUtils {

    private static final String PREF_DEVICE_IMEI = "pref_device_imei";
    private static final String PREF_DEVICE_FCM_ID = "pref_device_fcm_id";
    private static final String PREF_USER_DETAIL = "pref_user_detail";
    private static final String PREF_USER_DETAIL1 = "pref_user_detail1";
    private static final String PREF_IS_LOGIN = "pref_is_login";
    private static final String PREF_LOGIN_TOKEN = "pref_login_token";
    private static final String PREF_SOCIAL = "pref_social";
    private static final String PREF_IS_Attendance = "pref_is_attendance_editable";

    public static void clearAllPreferences(Context context) {

        String deviceIMEI = getDeviceIMEI(context);
        String deviceGCMId = getDeviceGcmId(context);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().clear().commit();
        setDeviceIMEI(context, deviceIMEI);
        setDeviceGcmId(context, deviceGCMId);

    }

 /*   public static void setUserDetails(Context context, UserDetails userDetails, boolean setLogin) {
        if(userDetails != null){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            if (setLogin)
            setUserLoginStatus(context, true);
          //  setToken(context, userDetails.getToken());
            Editor editor = sp.edit();
            String userDetailJson = new Gson().toJson(userDetails);
            editor.putString(PREF_USER_DETAIL1, userDetailJson);
            editor.commit();
        }
    }*/


    public static void setUserSocialDetails(Context context, UserProfileDetails userDetails, boolean setLogin) {
        if(userDetails != null){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            if (setLogin)
                setUserLoginStatus(context, true);
            //  setToken(context, userDetails.getToken());
            Editor editor = sp.edit();
            String userDetailJson = new Gson().toJson(userDetails);
            editor.putString(PREF_USER_DETAIL, userDetailJson);
            editor.commit();
        }
    }


    public static UserProfileDetails getUserProfileDetails(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userDetailJson=  sp.getString(PREF_USER_DETAIL, "");
        UserProfileDetails userProfileDetails= new Gson().fromJson(userDetailJson, UserProfileDetails.class);

        return userProfileDetails ;
    }

   /* public static UserDetails getUserDetails(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userDetailJson=  sp.getString(PREF_USER_DETAIL, "");
        UserDetails userDetails= new Gson().fromJson(userDetailJson, UserDetails.class);

        return userDetails;
    }*/

    public static boolean IsUserLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_IS_LOGIN, false);
    }

    public static void setUserLoginStatus(Context context,boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putBoolean(PREF_IS_LOGIN, value);
        editor.commit();
    }
    public static void setAttendanceEditable(Context context,boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putBoolean(PREF_IS_Attendance, value);
        editor.commit();
    }

    public static boolean getAttendanceEditable(Context context,boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_IS_Attendance, false);
    }

    public static String getDeviceGcmId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_DEVICE_FCM_ID, "");
    }

    public static void setDeviceGcmId(Context context, String deviceID) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(PREF_DEVICE_FCM_ID, deviceID);
        editor.commit();
    }

    public static String getDeviceIMEI(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_DEVICE_IMEI, "");
    }

    public static void setDeviceIMEI(Context context, String deviceIMEI) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(PREF_DEVICE_IMEI, deviceIMEI);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_LOGIN_TOKEN, "");
    }

    public static void setToken(Context context,String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(PREF_LOGIN_TOKEN, token);
        editor.commit();
    }

    public static Boolean isSocialLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_SOCIAL, false);
    }

    public static void setSocialLogin(Context context, Boolean isSocial) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putBoolean(PREF_SOCIAL, isSocial);
        editor.commit();
    }

}
