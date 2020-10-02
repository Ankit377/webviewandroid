package com.devparadigam.agrade.model.repositories;

import androidx.lifecycle.MutableLiveData;
import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.youtube.YoutubePlaylist;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YouTubeVideoRepository {
    private ApiServices mApiServices;
    private Disposable disposable;


    public YouTubeVideoRepository(ApiServices mApiServices) {
        this.mApiServices = mApiServices;

    }

    public MutableLiveData<Resource<YoutubePlaylist>> getVideoList(String key , String playlistId){
        final MutableLiveData<Resource<YoutubePlaylist>> productData = new MutableLiveData<>();
        mApiServices.getVideoList(key, playlistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<YoutubePlaylist>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        productData.setValue(new Resource<YoutubePlaylist>(Status.LOADING,null,"Loading"));
                    }

                    @Override
                    public void onSuccess(YoutubePlaylist response) {

                        if (response!=null) {
                            productData.setValue(new Resource<YoutubePlaylist>(Status.SUCCESS, response, "success"));

                        }else {
                            productData.setValue(new Resource<YoutubePlaylist>(Status.ERROR,null, "Something went wrong"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        productData.setValue(new Resource<YoutubePlaylist>(Status.ERROR, null, ""+e.getMessage()));
                    }
                });
        return productData;
    }
}
