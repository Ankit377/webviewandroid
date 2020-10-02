package com.devparadigam.agrade.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.databinding.QuestionBinding;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;

import java.util.List;

public class QuestionViewPagerAdapter extends PagerAdapter {

    Context context;
    List<TestQueModel> queList;

    public QuestionViewPagerAdapter(Context context, List<TestQueModel> queList) {
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
        QuestionBinding binding=QuestionBinding.inflate(LayoutInflater.from(context),container,false);
        binding.setQuestion(queList.get(i));

        binding.examGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1 :{
                        queList.get(i).setSelectedAns("a");
                    }
                    break;
                    case R.id.rb2 :{
                        queList.get(i).setSelectedAns("b");
                    }
                    break;
                    case R.id.rb3 :{
                        queList.get(i).setSelectedAns("c");
                    }
                    break;
                    case R.id.rb4 :{
                        queList.get(i).setSelectedAns("d");
                    }
                    break;
                    case R.id.rb5 :{
                        queList.get(i).setSelectedAns("e");
                    }
                    break;
                    default:{
                        queList.get(i).setSelectedAns(null);
                    }
                }
            }
        });

        binding.mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queList.get(i).getBookmarked()){
                    queList.get(i).setBookmarked(false);
                }else {
                    queList.get(i).setBookmarked(true);
                }
            }
        });

        binding.ClearResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //queList.get(i).setSelectedAns("x");
                binding.examGroup.clearCheck();
            }
        });

        ((ViewPager) container).addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((LinearLayout) obj);
    }


}
