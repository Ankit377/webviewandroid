package com.devparadigam.agrade.ui.activiries;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.MainBinding;
import com.devparadigam.agrade.ui.fragments.ExamTypeFragment;
import com.devparadigam.agrade.ui.fragments.HomeFragment;
import com.devparadigam.agrade.ui.fragments.StudyMaterialFragment;
import com.devparadigam.agrade.ui.fragments.SettingsFragment;
import com.devparadigam.agrade.ui.fragments.YouTubeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    MainBinding binding;
    ArrayList<FluidBottomNavigationItem> items;
    public static final int REQUEST_CODE = 1;
    BottomNavigationView bottomNavigationView;


    StudyMaterialFragment studyMaterialFragment = new StudyMaterialFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    HomeFragment homeFragment = new HomeFragment();
    YouTubeFragment youTubeFragment = new YouTubeFragment();
    ExamTypeFragment examTypeFragment = new ExamTypeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bottomNavigationView = binding.bottomNavigation;
       /* items = new ArrayList<>();
//        fluidBottomNavigation = binding.fluidBottomNavigation;


        items.add(
                new FluidBottomNavigationItem(
                        getString(R.string.nav_home),
                        ContextCompat.getDrawable(this, R.drawable.home)
                )
        );

        items.add(
                new FluidBottomNavigationItem(
                        getString(R.string.nav_study_material),
                        ContextCompat.getDrawable(this, R.drawable.metrail)
                )
        );
        items.add(
                new FluidBottomNavigationItem(
                        getString(R.string.nav_youtube),
                        ContextCompat.getDrawable(this, R.drawable.youtube_b)
                )
        );

        items.add(
                new FluidBottomNavigationItem(
                        getString(R.string.nav_contact_us),
                        ContextCompat.getDrawable(this, R.drawable.ic_settings)
                )
        );*/


      /*  binding.fluidBottomNavigation.setItems(items);
        binding.fluidBottomNavigation.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {

                String selectedTitle = items.get(i).getTitle();
                if (selectedTitle.equals(getString(R.string.nav_home))) {
                    defaultScreen();
                    //replaceFragment1(R.id.homeContainer, examTypeFragment, true );
                } else if (selectedTitle.equals(getString(R.string.nav_study_material))) {
                    replaceFragment1(R.id.homeContainer, studyMaterialFragment, false);
                } else if (selectedTitle.equals(getString(R.string.nav_youtube))) {
                    replaceFragment1(R.id.homeContainer, youTubeFragment, false);
                } else if (selectedTitle.equals(getString(R.string.nav_contact_us))) {
                    replaceFragment1(R.id.homeContainer, settingsFragment, false);
                } else {
                    replaceFragment1(R.id.homeContainer, examTypeFragment, false);
                }
               *//* switch (){
                    case getResources().getString(R.string.nav_home): {
//                        getString(R.string.nav_schedule);
                        replaceFragment1(R.Id.homeContainer,homeFragment,false);
                    }break;
                    case "Revenue": {
//                        getString(R.string.nav_wattel);
                        replaceFragment1(R.Id.homeContainer,studyMaterialFragment,false);
                    }break;
                    case "History": {
//                        getString(R.string.nav_home);
                        replaceFragment1(R.Id.homeContainer,historyFragment,false);
                    }break;
                    case "settings": {
//                        getString(R.string.nav_support);
                        replaceFragment1(R.Id.homeContainer,settingsFragment,false);
                    }break;
//                    default:
//                        getSupportFragmentManager().beginTransaction().replace(R.Id.homeContainer, studyMaterialFragment).commit();
                }*//*
            }
        });*/
        defaultScreen();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        defaultScreen();
                        return true;
                    case R.id.navigation_material:
                        replaceFragment1(R.id.homeContainer, studyMaterialFragment, false);
                        return true;
                    case R.id.navigation_youtube:
                        replaceFragment1(R.id.homeContainer, youTubeFragment, false);
                        return true;
                    case R.id.navigation_setting:
                        replaceFragment1(R.id.homeContainer, settingsFragment, false);
                        return true;
                }

                return false;
            }
        });

    }



    void defaultScreen() {

        replaceFragment1(R.id.homeContainer, examTypeFragment, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1011) {
            if (resultCode == RESULT_OK) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(studyMaterialFragment.getClass().getSimpleName());
                if (fragment != null)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        if (resultCode == 106) {
            if (resultCode == RESULT_OK) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(homeFragment.getClass().getSimpleName());
                if (fragment != null)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            for (Fragment fragment : getSupportFragmentManager().getFragments()){
                fragment.onActivityResult(requestCode,resultCode,data);
            }

        }
    }
}
