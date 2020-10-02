package com.devparadigam.agrade;

import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.utils.AppEnvironment;
import com.devparadigam.agrade.utils.LanguageModel;
import com.devparadigam.agrade.utils.NetworkUtility;
import com.devparadigam.agrade.utils.PreferenceUtils;
import com.devparadigam.agrade.utils.StaticData;

public class ApplicationParentClass extends MultiDexApplication {
    public static Context context;
    private static ApplicationParentClass mInstance;
    private ApiServices apiServices;
    private String TAG = ApplicationParentClass.class.getName();
    private static PreferenceUtils preferenceUtils;
    private TestModel testModel=null;
    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
//        MultiDex.install(this);
        super.onCreate();
        context = this;
        mInstance = this;

        basiSetupForApis();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageModel.setLocale(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageModel.setLocale(base));
        MultiDex.install(this);
    }



    public static Context getContext() {
        return context;
    }

    public static ApplicationParentClass getmInstance() {
        return mInstance;
    }

    public ApiServices getApiServices() {
        return apiServices;
    }

    public static PreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public void basiSetupForApis(){

        NetworkUtility.Builder networkBuilder = new NetworkUtility.Builder(StaticData.BASE_URL);
        //.withAccesToken(PreferenceUtils.getToken(mContext));
        networkBuilder.withConnectionTimeout(60)
                .withReadTimeout(10)
                .withShouldRetryOnConnectionFailure(true)
                .build();


        apiServices = networkBuilder.getRetrofit().create(ApiServices.class);
    }

    public TestModel getTestModel() {
        return testModel;
    }

    public void setTestModel(TestModel testModel) {
        this.testModel = testModel;
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }
}
