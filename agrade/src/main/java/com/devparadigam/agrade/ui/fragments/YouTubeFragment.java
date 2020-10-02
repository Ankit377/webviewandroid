package com.devparadigam.agrade.ui.fragments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.base.BaseFragment;
import com.devparadigam.agrade.databinding.YoutTubeFragBinding;
import com.devparadigam.agrade.model.response.youtube.Item;
import com.devparadigam.agrade.model.response.youtube.YoutubePlaylist;
import com.devparadigam.agrade.ui.activiries.YouTubePlayerActivity;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class YouTubeFragment extends BaseFragment {

    YoutTubeFragBinding binding;
    ProgressDialog progressDialog;
    ArrayList<Item> videoDetails;

    GenericListAdapter<Item> listAdapter;
    ApiServices apiServices;
    Disposable videoDisposer;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable
            ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        binding = YoutTubeFragBinding.inflate(inflater,container,false);

        setUpRetroFit();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        observerGetVideos(getString(R.string.youTube_Api_key),getString(R.string.playList_Key));
    }

    private void setUpRetroFit() {
        Retrofit retrofit = new Retrofit.Builder()
              //  .client(okHttpClient)
                .baseUrl(StaticData.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiServices=retrofit.create(ApiServices.class);
    }

    void observerGetVideos(String key, String playlistId){

        apiServices.getVideoList(key,playlistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<YoutubePlaylist>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        videoDisposer=d;
                        enableLoadingBar(true);
                    }

                    @Override
                    public void onSuccess(YoutubePlaylist youtubePlaylist) {
                        if (videoDisposer!=null)
                            videoDisposer.dispose();
                        enableLoadingBar(false);

                        if (youtubePlaylist!=null) {
                            if (youtubePlaylist.getItems() != null)
                                if (!youtubePlaylist.getItems().isEmpty()) {
                                    videoDetails = (ArrayList<Item>) youtubePlaylist.getItems();
                                    listAdapter = new GenericListAdapter<Item>(videoDetails, R.layout.youtube_list_layout, new GenericListAdapter.OnListItemClickListener<Item>() {
                                        @Override
                                        public void onListItemClicked(Item selItem, @Nullable Object extra, int position) {
                                            if (extra!=null && extra instanceof View){
                                                switch (((View)extra).getId()){
                                                    case R.id.lyt_parent:{
                                                        String videoId =selItem.getContentDetails().getVideoId();
                                                        Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
                                                        intent.putExtra("Id", videoId);
                                                        startActivity(intent);
                                                    }break;
                                                }
                                            }
                                        }
                                    });
                                    binding.recyclerViewList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                                    binding.recyclerViewList.setAdapter(listAdapter);
                        }else {showToast("No item found in playlist");}
                        }else {
                            showToast("Something went wrong");
                        }

                    }


                    @Override
                    public void onError(Throwable e) {
                        if (videoDisposer!=null)
                            videoDisposer.dispose();

                        enableLoadingBar(false);
                    }
                });
    }
}
