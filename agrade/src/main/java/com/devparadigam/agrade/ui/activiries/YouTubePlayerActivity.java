package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.databinding.YouTubePlayerBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerBinding binding;
    YouTubePlayerView youTubePlayerView;
    private static final int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_you_tube_player);

        youTubePlayerView = binding.youtubeView;
        youTubePlayerView.initialize(getString(R.string.youTube_Api_key),this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        String videoId = getIntent().getStringExtra("Id");
        youTubePlayer.cueVideo(videoId);
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {

            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();

        } else {

            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_REQUEST) {

            getYouTubePlayerProvider().initialize("AIzaSyCNlyeDkSVMm7SnnHPaxIKPrdiVhr5NhBE", this);

        }

    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {

        return youTubePlayerView;

    }
}
