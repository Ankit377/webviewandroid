package com.devparadigam.agrade.ui.activiries;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.LoginBinding;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.shambhu.social.SocialLogin;
import com.shambhu.social.SocialLoginResultListener;
import com.shambhu.social.SocialLoginType;
import com.shambhu.social.UserModel;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends BaseActivity implements SocialLoginResultListener {

    private static final String TAG = "Login_activity";
    private UserModel userModelSocial;
    private LoginBinding binding;
    private UserViewModel viewModel;
    private EditText phnText;
    private String phone="", userName="";

    String name="",email="",mobile="",account_type,social_id,device_id;
    String mob_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_id = task.getResult().getToken();
                    }
                });
//        token = mPref.getToken(LoginActivity.this);
        setUpViewModel();
        submit();
        googleLogin();
        faceBookLogin();
        truecallerLogin();

    }

    boolean validate(){


        if (binding.editTextname.getVisibility()==View.VISIBLE){
            if (userName.equalsIgnoreCase("")){
                binding.editTextname.setError("please check the Username");
                binding.editTextname.requestFocus();
                return false;
            }
            if (!phone.matches("[0-9]{10}")){
                binding.editTextmobile.setError("please check the Mobile no.");
                binding.editTextmobile.requestFocus();
                return false;
            }
        }else {
            if (!phone.matches("[0-9]{10}")){
                binding.editTextmobile.setError("please check the Mobile no.");
                binding.editTextmobile.requestFocus();
                return false;
            }
        }

        return true;
    }


    void googleLogin(){

        binding.googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SocialLogin.setGoogleFragment(LoginActivity.this,LoginActivity.this);
                enableLoadingBar(true);
            }
        });
    }

    void faceBookLogin(){

        binding.facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SocialLogin.setFaceBookFragment(LoginActivity.this,LoginActivity.this);
                enableLoadingBar(true);
            }
        });
    }

    void truecallerLogin(){
     /*   binding.truecallerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SocialLogin.setTrueCallerFragment(LoginActivity.this,LoginActivity.this);
                enableLoadingBar(true);
            }
        });*/
    }


    void submit(){
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = binding.editTextmobile.getText().toString();
                userName = binding.editTextname.getText().toString();
                if (validate()){

                   if (binding.editTextname.getVisibility()==View.GONE){
                        observercCheckRegistration(phone);
                    }else {
                       enableLoadingBar(true);
                       Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                       intent.putExtra("phone",phone);
                       intent.putExtra("name",userName);
                       intent.putExtra("device_id",device_id);
                       startActivity(intent);
                       enableLoadingBar(false);

                   }

                                   }
            }
        });
    }

    @Override
    public void onLoginSuccess(UserModel userModel) {
        Log.e("Socail response", "" + userModel.getId());
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        enableLoadingBar(false);
        name = userModel.getName();
        email = userModel.getEmail();
        mobile = userModel.getMobile_no();
        account_type = userModel.getSocialLoginType().toString();
        social_id = userModel.getId();


        observeUserLogin(name, account_type, social_id, device_id, email,mobile);
    }
    @Override
    public void onLoginFailure(SocialLoginType type, String reason) {
        Log.e("Socail response","Error in "+type+"\n"+reason);
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        enableLoadingBar(false);
    }

    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observeUserLogin(String username, String account_type, String social_id, String device_id, String email, String mobile_no){
        viewModel.useraLogin(username,account_type, social_id, device_id, email, mobile_no)
                .observe(this, new Observer<Resource<UserProfileDetails>>() {
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

                            if (email.equals("") && mobile_no.equals("")){

                                Toast.makeText(LoginActivity.this,userDetailsResource.message, Toast.LENGTH_SHORT).show();

                            }else{
                                UserProfileDetails userProfileDetails = userDetailsResource.data;
                                mPref.setUserSocialDetails(LoginActivity.this,userProfileDetails,true);
                                mPref.setToken(LoginActivity.this, userProfileDetails.getToken());

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this,userDetailsResource.message, Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            showSnackBar(binding.getRoot(),userDetailsResource.message);
                        }

                    }
                }
            }
        });

    }

    void observercCheckRegistration(String mobile_no){
        viewModel.checkRegistration(mobile_no).observe(this, new Observer<Resource<UserProfileDetails>>() {
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
                            if (userDetailsResource.status == Status.SUCCESS) {
                                name = userDetailsResource.data.getName();
                                Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                                intent.putExtra("phone",phone);
                                intent.putExtra("device_id",device_id);
                                intent.putExtra("name", name);
                                startActivity(intent);
                            showToast(userDetailsResource.message);
                        }
                    }break;
                    case FALSE:{

                            enableLoadingBar(false);
                            binding.editTextname.setVisibility(View.VISIBLE);
                            binding.editTextname.requestFocus();
                            showToast(userDetailsResource.message);

                    }
                }
            }
        });

    }
}
