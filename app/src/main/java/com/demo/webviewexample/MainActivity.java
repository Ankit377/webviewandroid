package com.demo.webviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = (WebView)findViewById(R.id.WebView);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        String url = "https://ankit377.github.io/scholarship";
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}