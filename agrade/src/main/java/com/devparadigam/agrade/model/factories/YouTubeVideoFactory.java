package com.devparadigam.agrade.model.factories;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.devparadigam.agrade.model.repositories.YouTubeVideoRepository;
import com.devparadigam.agrade.viewmodels.VideoViewModel;

public class YouTubeVideoFactory implements ViewModelProvider.Factory {

    YouTubeVideoRepository youTubeVideoRepository;

    public YouTubeVideoFactory(YouTubeVideoRepository youTubeVideoRepository) {
        this.youTubeVideoRepository = youTubeVideoRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoViewModel.class)) {
            return (T) new VideoViewModel(youTubeVideoRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");

    }
}
