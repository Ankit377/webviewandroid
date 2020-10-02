package com.devparadigam.agrade.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devparadigam.agrade.model.response.StudyMetarialModel;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.TransactionModel;
import com.devparadigam.agrade.model.response.UserData;
import com.devparadigam.agrade.model.response.UserDetails;
import com.devparadigam.agrade.model.response.UserProfileDetails;

import java.util.List;

import okhttp3.MultipartBody;

public class UserViewModel extends ViewModel {
    UserRepository userRepository;
    LiveData<Resource<UserViewModel>> liveData;


    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        liveData = new MediatorLiveData<>();
        ((MediatorLiveData<Resource<UserViewModel>>) liveData).postValue(new Resource<UserViewModel>(Status.IDEAL,null,""));
    }

    public MutableLiveData<Resource<UserDetails>> callLogin(String name, String email, String mobile, String address, String password, String c_password){
        return userRepository.callLogin(name,email,mobile,address,password,c_password);
    }

    public MutableLiveData<Resource<UserData>> callSocilaLogin(String fullname, String email, String mobile_no, String login_type, String device_id){
        return userRepository.callSocialLogin(fullname,email,mobile_no,login_type,device_id);
    }

    public MutableLiveData<Resource<UserProfileDetails>> useraLogin(String username, String account_type, String social_id, String device_id, String email, String mobile_no){
        return userRepository.userLogin(username,account_type, social_id, device_id, email, mobile_no);
    }

    public MutableLiveData<Resource<UserProfileDetails>> checkRegistration(String mobile_no){
        return userRepository.checkRegistration(mobile_no);
    }

    public MutableLiveData<Resource<UserProfileDetails>> getProfile(String token){
        return userRepository.getProfile(token);
    }

    public MutableLiveData<Resource<String>> profilePhoto(String token, MultipartBody.Part photo){
        return userRepository.profilePhoto(token,photo);
    }

    public MutableLiveData<Resource<List<StudyMetarialModel>>> getStudyMaterial(String token){
        return userRepository.getStudyMetarial(token);
    }

    public MutableLiveData<Resource<TransactionModel>> getTransaction(String token, String amount, String examId, String transactionId, String response ){
        return userRepository.getTransaction(token,amount,examId,transactionId,response);
    }
}
