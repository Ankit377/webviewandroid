package com.devparadigam.agrade.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.devparadigam.agrade.model.repositories.YouTubeVideoRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.youtube.YoutubePlaylist;

public class VideoViewModel extends ViewModel {

    YouTubeVideoRepository youTubeVideoRepository;
    LiveData<Resource<YoutubePlaylist>> productLiveData;

    public VideoViewModel(YouTubeVideoRepository youTubeVideoRepository) {
        this.youTubeVideoRepository = youTubeVideoRepository;

        productLiveData = new MediatorLiveData<>();
        ((MediatorLiveData<Resource<YoutubePlaylist>>) productLiveData).postValue(new Resource<YoutubePlaylist>(Status.IDEAL,null,""));

    }

    public MutableLiveData<Resource<YoutubePlaylist>> getVideoList(String key , String playlistId){
        return youTubeVideoRepository.getVideoList(key, playlistId);
    }
}
