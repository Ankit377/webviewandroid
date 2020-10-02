package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.ExamTypeBinding;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.viewmodels.UserViewModel;

public class ExamTypeActivity extends BaseActivity {

    ExamTypeBinding binding;
    UserViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = DataBindingUtil.setContentView(this,R.layout.activity_exam_type);
       setUpViewModel();
       binding.banking.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               banking();
           }
       });
       binding.ssc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ssc();
           }
       });
       binding.railway.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            railway();
           }
       });




    }

    void banking(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("parentcategory","8");
        startActivity(intent);
    }
    void ssc(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("parentcategory","12");
        startActivity(intent);
    }
    void railway(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("parentcategory","13");
        startActivity(intent);
    }

    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
