package com.devparadigam.agrade.ui.activiries;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.devparadigam.agrade.R;

public class ContactUrlActivity extends AppCompatActivity {

    WebView browser;
    Context context;
    AppCompatActivity activity;
    SwipeRefreshLayout refresh;
    String currentUrl = "";
   // ValueCallback<Uri[]> mfilePathCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.web_url_activity);
        String url = getIntent().getStringExtra("url");
        browser = (WebView) findViewById(R.id.browser);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        browser.setWebViewClient(new AppWebViewClient());
        browser.loadUrl(url);
        refresh.setRefreshing(true);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                browser.loadUrl(currentUrl);
            }
        });

        /*browser.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mfilePathCallback=filePathCallback;
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
        mfilePathCallback=new ValueCallback<Uri[]>() {
            @Override
            public void onReceiveValue(Uri[] uris) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               // intent.setType("star/star");
                startActivityForResult(intent, 101);
            }
        };*/

    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }

    class AppWebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //loadImage.show();
            refresh.setRefreshing(true);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            //
            //
            //browser.clearCache(true);
//            loadImage.hide();
            refresh.setRefreshing(false);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            if(Uri.parse(url).getHost().length() == 0) {
                return false;
            }
            currentUrl = url;
            browser.loadUrl(url);
            return true;
        }



    }
}
