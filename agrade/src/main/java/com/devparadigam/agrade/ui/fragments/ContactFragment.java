package com.devparadigam.agrade.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devparadigam.agrade.BuildConfig;
import com.devparadigam.agrade.base.BaseFragment;
import com.devparadigam.agrade.databinding.ContactusBinding;
import com.devparadigam.agrade.ui.activiries.ContactUrlActivity;

public class ContactFragment extends BaseFragment {

    ContactusBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ContactusBinding.inflate(inflater,container,false);
        String url = binding.urlText.getText().toString();
        binding.buildversion.setText(""+BuildConfig.VERSION_NAME);
        binding.urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUrlActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    }
