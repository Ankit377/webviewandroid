package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.SplashBinding;
import com.bumptech.glide.Glide;

public class SplashActivity extends BaseActivity {

    SplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);
       // Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
      //  binding.splashVideo.setVideoURI(video);
      //  binding.splashVideo.start();

        Glide.with(this).load(R.raw.splashgif).into(binding.imageView);

      /*  binding.splashVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                reDirect();
            }
        });*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reDirect();

            }
        }, 5000);

    }

    void reDirect() {

        if (mPref.IsUserLogin(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }

       /* Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("price", 200);
        startActivity(intent);*/



    }
}
