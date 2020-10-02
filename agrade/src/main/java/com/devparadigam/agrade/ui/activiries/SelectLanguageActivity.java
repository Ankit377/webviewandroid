package com.devparadigam.agrade.ui.activiries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.databinding.LanguageBinding;
import com.devparadigam.agrade.utils.LanguageModel;

public class SelectLanguageActivity extends AppCompatActivity {

    LanguageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_language);
//        getIntentData();
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.hide();

        binding.engtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LanguageModel.setNewLocale(getApplicationContext(), LanguageModel.ENGLISH);

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();



            }
        });

        binding.hindiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LanguageModel.setNewLocale(getApplicationContext(), LanguageModel.HINDI);

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();


            }
        });

    }
}
