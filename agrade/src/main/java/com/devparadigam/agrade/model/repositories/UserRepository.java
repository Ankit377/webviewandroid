package com.devparadigam.agrade.model.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.apiservices.JsonArrayResponse;
import com.devparadigam.agrade.apiservices.JsonObjectResponse;
import com.devparadigam.agrade.model.response.StudyMetarialModel;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.TransactionModel;
import com.devparadigam.agrade.model.response.UserData;
import com.devparadigam.agrade.model.response.UserDetails;
import com.devparadigam.agrade.model.response.UserProfileDetails;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class UserRepository {

    private ApiServices apiServices;
    Disposable disposable, checkUserRegistrationDisposer, getProfileDisposer, profilePhotoDisposal,studyMetarialDiasposal,gteTransactionDisposal;

    public UserRepository(ApiServices apiServices) {
        this.apiServices = apiServices;
    }


    public MutableLiveData<Resource<UserDetails>> callLogin(String name, String email, String mobile, String address, String password, String c_password) {

        final MutableLiveData<Resource<UserDetails>> userData = new MutableLiveData<>();
        apiServices.login(name, email, mobile, address, password, c_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<UserDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        disposable = d;
                        userData.setValue(new Resource<UserDetails>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<UserDetails> loginResponse) {
                        if (loginResponse != null) {

                            if (loginResponse.getSuccess())
                                userData.setValue(new Resource<UserDetails>(Status.SUCCESS, loginResponse.data, "" + loginResponse.getMessage()));
                            if (loginResponse.data != null) {
                            } else
                                userData.setValue(new Resource<UserDetails>(Status.ERROR, null, "" + loginResponse.getMessage()));

                        } else {
                            userData.setValue(new Resource<UserDetails>(Status.ERROR, null, "Something went wrong"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userData.setValue(new Resource<UserDetails>(Status.ERROR, null, "" + e.getMessage()));
                    }
                });
        return userData;
    }

    public MutableLiveData<Resource<UserData>> callSocialLogin(String fullname, String email, String mobile_no, String login_type, String device_id) {

        final MutableLiveData<Resource<UserData>> userData = new MutableLiveData<>();
        apiServices.Sociallogin(fullname, email, mobile_no, login_type, device_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<UserData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        disposable = d;
                        userData.setValue(new Resource<UserData>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<UserData> loginResponse) {
                        if (loginResponse != null) {
                            if (loginResponse.getSuccess()) {
                                if (loginResponse.data != null) {
                                    userData.setValue(new Resource<UserData>(Status.SUCCESS, loginResponse.data, "" + loginResponse.getMessage()));
                                } else {
                                    userData.setValue(new Resource<UserData>(Status.ERROR, null, "" + loginResponse.getMessage()));
                                }
                            } else {
                                userData.setValue(new Resource<UserData>(Status.ERROR, null, "Something went wrong"));
                            }
                        } else {
                            userData.setValue(new Resource<UserData>(Status.ERROR, null, "Something went wrong"));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        userData.setValue(new Resource<UserData>(Status.ERROR, null, "" + e.getMessage()));
                    }
                });
        return userData;
    }

    public MutableLiveData<Resource<UserProfileDetails>> userLogin(String username, String account_type, String social_id, String device_id, String email, String mobile_no) {

        final MutableLiveData<Resource<UserProfileDetails>> userData = new MutableLiveData<>();
        apiServices.userLogin(username,account_type, social_id, device_id, email, mobile_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<UserProfileDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        disposable = d;
                        userData.setValue(new Resource<UserProfileDetails>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<UserProfileDetails> loginResponse) {
                        if (loginResponse != null) {
                            if (loginResponse.getSuccess()) {
                                if (loginResponse.data != null) {
                                    userData.setValue(new Resource<UserProfileDetails>(Status.SUCCESS, loginResponse.data, "" + loginResponse.getMessage()));
                                } else {
                                    userData.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, "" + loginResponse.getMessage()));
                                }
                            } else {
                                userData.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, "Something went wrong"));
                            }
                        } else {
                            userData.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, "Something went wrong"));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        userData.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, "" + e.getMessage()));
                    }
                });
        return userData;
    }

    public MutableLiveData<Resource<UserProfileDetails>> checkRegistration(String mobile_no) {
        final MutableLiveData<Resource<UserProfileDetails>> check = new MutableLiveData<>();
        apiServices.checkRegistration(mobile_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<UserProfileDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        checkUserRegistrationDisposer = d;
                        check.setValue(new Resource<UserProfileDetails>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<UserProfileDetails> userProfileDetailsJsonArrayResponse) {
                        if (userProfileDetailsJsonArrayResponse != null) {
                            if (userProfileDetailsJsonArrayResponse.getSuccess()) {
                                check.setValue(new Resource<UserProfileDetails>(Status.SUCCESS, userProfileDetailsJsonArrayResponse.data, userProfileDetailsJsonArrayResponse.getMessage()));
                            } else {
                                check.setValue(new Resource<UserProfileDetails>(Status.FALSE, null, userProfileDetailsJsonArrayResponse.getMessage()));
                            }
                        } else {
                            check.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, userProfileDetailsJsonArrayResponse.getMessage()));
                        }

                        if (checkUserRegistrationDisposer != null)
                            checkUserRegistrationDisposer.dispose();


                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (checkUserRegistrationDisposer != null)
                            checkUserRegistrationDisposer.dispose();

                        check.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return check;
    }

    public MutableLiveData<Resource<UserProfileDetails>> getProfile(String token) {

        final MutableLiveData<Resource<UserProfileDetails>> profile = new MutableLiveData<>();
        apiServices.getProfile(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<UserProfileDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getProfileDisposer = d;
                        profile.setValue(new Resource<UserProfileDetails>(Status.LOADING, null, "loalding"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<UserProfileDetails> userProfileDetailsJsonObjectResponse) {
                        if (userProfileDetailsJsonObjectResponse != null) {
                            if (userProfileDetailsJsonObjectResponse.getSuccess()) {
                                if (userProfileDetailsJsonObjectResponse.data != null) {
                                    profile.setValue(new Resource<UserProfileDetails>(Status.SUCCESS, userProfileDetailsJsonObjectResponse.data, "" + userProfileDetailsJsonObjectResponse.getMessage()));
                                } else {
                                    profile.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, "" + userProfileDetailsJsonObjectResponse.getMessage()));
                                }
                            } else {
                                profile.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, ""+userProfileDetailsJsonObjectResponse.getMessage()));
                            }
                        } else {
                            profile.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, ""+userProfileDetailsJsonObjectResponse.getMessage()));

                        }
                        if (getProfileDisposer != null)
                            getProfileDisposer.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (getProfileDisposer != null)
                            getProfileDisposer.dispose();
                        profile.setValue(new Resource<UserProfileDetails>(Status.ERROR, null, ""+e.getMessage()));
                    }
                });
        return profile;
    }

    public MutableLiveData<Resource<String>> profilePhoto(String token, MultipartBody.Part photo) {

        final MutableLiveData<Resource<String>> photoChange = new MutableLiveData<>();
        apiServices.setProfilePhoto(token,photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        profilePhotoDisposal = d;
                        photoChange.setValue(new Resource<String>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<String> response) {
                        if (profilePhotoDisposal != null)
                            profilePhotoDisposal.dispose();
                        if (response != null) {
                            if (response.getSuccess()) {
                                if (response.data != null) {
                                    photoChange.setValue(new Resource<String>(Status.SUCCESS, response.data, "" + response.getMessage()));
                                } else {
                                    photoChange.setValue(new Resource<String>(Status.ERROR, null, "" + response.getMessage()));
                                }
                            } else {
                                photoChange.setValue(new Resource<String>(Status.ERROR, null, "" + response.getMessage()));
                            }
                        } else {
                            photoChange.setValue(new Resource<String>(Status.ERROR, null, "" + response.getMessage()));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (profilePhotoDisposal != null)
                            profilePhotoDisposal.dispose();
                        photoChange.setValue(new Resource<String>(Status.ERROR, null, "" + e.getMessage()));
                    }
                });
        return photoChange;
    }

    public MutableLiveData<Resource<List<StudyMetarialModel>>> getStudyMetarial(String token) {
        final MutableLiveData<Resource<List<StudyMetarialModel>>> examType = new MutableLiveData<>();
        apiServices.getStudyMetarial(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonArrayResponse<StudyMetarialModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        studyMetarialDiasposal = d;
                        examType.setValue(new Resource<List<StudyMetarialModel>>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonArrayResponse<StudyMetarialModel> examTypeJsonArrayResponse) {
                        if (studyMetarialDiasposal != null)
                            studyMetarialDiasposal.dispose();

                        if (examTypeJsonArrayResponse != null) {
                            if (examTypeJsonArrayResponse.getSuccess()) {
                                if (examTypeJsonArrayResponse.list==null)
                                    examType.setValue(new Resource<List<StudyMetarialModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                                else if (examTypeJsonArrayResponse.list.isEmpty())
                                    examType.setValue(new Resource<List<StudyMetarialModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                                else
                                    examType.setValue(new Resource<List<StudyMetarialModel>>(Status.SUCCESS, examTypeJsonArrayResponse.list, examTypeJsonArrayResponse.getMessage()));
                            } else {
                                examType.setValue(new Resource<List<StudyMetarialModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                            }
                        } else {
                            examType.setValue(new Resource<List<StudyMetarialModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (studyMetarialDiasposal != null)
                            studyMetarialDiasposal.dispose();

                        examType.setValue(new Resource<List<StudyMetarialModel>>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return examType;
    }

    public MutableLiveData<Resource<TransactionModel>> getTransaction(String token, String amount, String examId, String transactionId, String response) {

        final MutableLiveData<Resource<TransactionModel>> transaction = new MutableLiveData<>();
        apiServices.getTransaction(token,amount,examId,transactionId,response)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<TransactionModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        gteTransactionDisposal = d;
                        transaction.setValue(new Resource<TransactionModel>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<TransactionModel> response) {
                        if (gteTransactionDisposal != null)
                            gteTransactionDisposal.dispose();
                        if (response != null) {
                            if (response.getSuccess()) {
                                if (response.data != null) {
                                    transaction.setValue(new Resource<TransactionModel>(Status.SUCCESS, response.data, "" + response.getMessage()));
                                } else {
                                    transaction.setValue(new Resource<TransactionModel>(Status.ERROR, null, "" + response.getMessage()));
                                }
                            } else {
                                transaction.setValue(new Resource<TransactionModel>(Status.ERROR, null, "" + response.getMessage()));
                            }
                        } else {
                            transaction.setValue(new Resource<TransactionModel>(Status.ERROR, null, "" + response.getMessage()));

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (gteTransactionDisposal != null)
                            gteTransactionDisposal.dispose();
                        transaction.setValue(new Resource<TransactionModel>(Status.ERROR, null, "" + e.getMessage()));
                    }
                });
        return transaction;
    }

}
