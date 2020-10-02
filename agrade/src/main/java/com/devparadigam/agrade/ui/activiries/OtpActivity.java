package com.devparadigam.agrade.ui.activiries;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.OtpBinding;


import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.devparadigam.agrade.utils.CountryPhoneCodesUtils;

import com.devparadigam.agrade.utils.PreferenceUtils;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class OtpActivity extends BaseActivity {

    private OtpBinding binding;
    FirebaseAuth mAuth;
    private UserViewModel viewModel;
    //    private PinView otpview;
//    private OTPVIEW otpview;
    private OtpView otpview;
    private String CountryID, mVerificationId, smsCode, countryCode, phone, username, device_id, email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        otpview = binding.otpView;
        phone = getIntent().getStringExtra("phone");
        username = getIntent().getStringExtra("name");
        device_id = getIntent().getStringExtra("device_id");
        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = CountryPhoneCodesUtils.getPhone(manager.getSimCountryIso());
        CountryID = manager.getSimCountryIso().toUpperCase();

        setUpViewModel();
        initFirebase();

        send();
        reSend();

    }

    void send() {
        binding.send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                enableLoadingBar(true);

                if (isValidOtp()) {

                    signInWithPhoneAuthCredential(otpview.getText().toString());

                } else {
                    enableLoadingBar(false);
                    Toast.makeText(OtpActivity.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    boolean isValidOtp() {
        if (TextUtils.isEmpty(otpview.getText().toString()) || otpview.getText().length() < 6) {
            return false;
        }
        return true;
    }

    void reSend() {
        binding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFirebase();
            }
        });
    }


    void initFirebase() {

        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance();
        phoneAuthProvider.verifyPhoneNumber(
                countryCode + " " + phone,
                60,
                TimeUnit.SECONDS,
                OtpActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.e("fb complete", "done" + smsCode);
//                        observeUserLogin(username, email, phone, "mobile", phone,token);
                        observeUserLogin(username, "Mobile", phone, device_id, email,phone);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.e("FireBase EX :", e.getMessage());
                        Toast.makeText(OtpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        mVerificationId = s;

                        Toast.makeText(OtpActivity.this, "Otp Sent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        Log.e("firebase timeout", s);
                    }
                });
    }


    private void signInWithPhoneAuthCredential(String otp) {

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, otp);


        if (smsCode == null)
            smsCode = phoneAuthCredential.getSmsCode();

        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            enableLoadingBar(false);
                            observeUserLogin(username, "Mobile", phone, device_id, email,phone);


                        } else {
                            enableLoadingBar(false);
                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Toast.makeText(OtpActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observeUserLogin(String username, String account_type, String social_id, String device_id, String email, String mobile_no) {
        viewModel.useraLogin(username,account_type, social_id, device_id, email, mobile_no).observe(this, new Observer<Resource<UserProfileDetails>>() {
            @Override
            public void onChanged(Resource<UserProfileDetails> userDetailsResource) {
                switch (userDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (userDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (userDetailsResource.message != null)
                            showToast(userDetailsResource.message);

                        if (userDetailsResource.data != null) {

                            if (email.equals("") && mobile_no.equals("")) {

                                Toast.makeText(OtpActivity.this, userDetailsResource.message, Toast.LENGTH_SHORT).show();

                            } else {
                                UserProfileDetails userProfileDetails = userDetailsResource.data;
                                PreferenceUtils.setUserSocialDetails(OtpActivity.this, userProfileDetails, true);
                                PreferenceUtils.setToken(OtpActivity.this, userProfileDetails.getToken());

                                Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                intent.putExtra("phone", phone);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(OtpActivity.this, userDetailsResource.message, Toast.LENGTH_SHORT).show();
                                enableLoadingBar(false);
                            }

                        } else {
                            enableLoadingBar(false);
                            showSnackBar(binding.getRoot(), userDetailsResource.message);
                        }

                    }
                }
            }
        });

    }
}
