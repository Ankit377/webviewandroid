package com.devparadigam.agrade.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.devparadigam.agrade.databinding.ResultdetailsBinding;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;

import java.util.List;

public class ResultDetailsViewPagerAdapter extends PagerAdapter {

    Context context;
    List<TestQueModel> queList;

    public ResultDetailsViewPagerAdapter(Context context, List<TestQueModel> queList) {
        this.context = context;
        this.queList = queList;
    }

    @Override
    public int getCount() {
        return queList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container,final int i) {
        ResultdetailsBinding binding= ResultdetailsBinding.inflate(LayoutInflater.from(context),container,false);
        binding.setQuestion(queList.get(i));

        ((ViewPager) container).addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((LinearLayout) obj);
    }


}
