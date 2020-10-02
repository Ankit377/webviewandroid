package com.devparadigam.agrade.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseFragment;
import com.devparadigam.agrade.databinding.SettingsBinding;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.devparadigam.agrade.ui.activiries.LoginActivity;
import com.devparadigam.agrade.ui.activiries.SelectLanguageActivity;
import com.devparadigam.agrade.ui.activiries.TestHistoryActivity;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;


public class SettingsFragment extends BaseFragment {

    SettingsBinding binding;
    UserViewModel userViewModel;
    String token;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =SettingsBinding.inflate(inflater,container,false);
        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(getActivity()).getToken();
        setUpViewModel();
        observerGetProfile(StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(getActivity()).getToken());

        ContactFragment contactFragment = new ContactFragment();
        binding.logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPref.clearAllPreferences(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                /*mPref.setUserSocialDetails(getActivity(),null,false);
                mPref.setToken(getActivity(),null);
                mPref.setUserLoginStatus(getActivity(),false);*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        binding.contactCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment1(R.id.homeContainer, contactFragment,true);
            }
        });
        binding.myProfilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);*/
            }
        });


        binding.testHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestHistoryActivity.class);
                startActivity(intent);
            }
        });

        binding.imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
//                    .setCropShape(CropImageView.CropShape.OVAL)
                        .start(getActivity());
            }
        });

        binding.languageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectLanguageActivity.class));
            }
        });

        return binding.getRoot();
    }
    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void observerGetProfile(String token){
        userViewModel.getProfile(token).observe(this, new Observer<Resource<UserProfileDetails>>() {
            @Override
            public void onChanged(Resource<UserProfileDetails> userDetailsResource) {
                switch (userDetailsResource.status){
                    case IDEAL:{ }break;
                    case LOADING:{
                        enableLoadingBar(true);
                    }break;
                    case ERROR:{
                        enableLoadingBar(false);
                        if (userDetailsResource.message!=null)
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                    }break;
                    case SUCCESS:{
                        enableLoadingBar(false);
                        if (userDetailsResource.message!=null)
                            showToast(userDetailsResource.message);

                        if (userDetailsResource.data!=null) {


                            UserProfileDetails userProfileDetails = userDetailsResource.data;
                            binding.setProfile(userProfileDetails);
                            Toast.makeText(getActivity(),userDetailsResource.message, Toast.LENGTH_SHORT).show();


                        }else{
                            showSnackBar(binding.getRoot(),userDetailsResource.message);
                        }

                    }
                }
            }
        });

    }
    void observerProfilePhoto(String token, MultipartBody.Part photo){
        userViewModel.profilePhoto(token,photo).observe(this, new Observer<Resource<String>>() {

            public void onChanged(Resource<String> userDetailsResource) {
                switch (userDetailsResource.status){
                    case IDEAL:{ }break;
                    case LOADING:{
                        enableLoadingBar(true);
                    }break;
                    case ERROR:{
                        enableLoadingBar(false);
                        if (userDetailsResource.message!=null)
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                    }break;
                    case SUCCESS:{
                        enableLoadingBar(false);
                        if (userDetailsResource.message!=null)
                            showToast(userDetailsResource.message);

                        if (userDetailsResource.data!=null) {

                          /*  if (email.equals("") && mobile_no.equals("")){

                                Toast.makeText(EmailActivity.this,userDetailsResource.message, Toast.LENGTH_SHORT).show();

                            }else{*/
                            UserProfileDetails userDetails= mPref.getUserProfileDetails(getActivity());
                            userDetails.setProfile_photo(userDetailsResource.data);
                            mPref.setUserSocialDetails(getActivity(),userDetails,true);
                            showToast(userDetailsResource.message);


                        }else{
                            showSnackBar(binding.getRoot(),userDetailsResource.message);
                        }

                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri uri = result.getUri();
                Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(binding.imgUserProfile);
                File file = new File(uri.getPath());
                observerProfilePhoto(token,getBodyFromImageFile(file));

            }
        }
    }
}
