package com.devparadigam.agrade.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseFragment;
import com.devparadigam.agrade.databinding.ExamTypeFragBinding;
import com.devparadigam.agrade.ui.activiries.TestHistoryActivity;

public class ExamTypeFragment extends BaseFragment {

    ExamTypeFragBinding binding;
    HomeFragment homeFragment;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ExamTypeFragBinding.inflate(inflater,container,false);
        homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        binding.linearBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                bundle.putString("examId","8");
                homeFragment.setArguments(bundle);
                replaceFragment1(R.id.homeContainer,homeFragment,true);

            }
        });

        binding.linearSsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("examId","12");
                homeFragment.setArguments(bundle);
                replaceFragment1(R.id.homeContainer,homeFragment,true);

            }
        });

        binding.linearRailway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("examId","13");
                homeFragment.setArguments(bundle);
                replaceFragment1(R.id.homeContainer,homeFragment,true);

            }
        });

        binding.linearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TestHistoryActivity.class));

            }
        });

        return binding.getRoot();
    }
}
