package com.devparadigam.agrade.model.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.apiservices.InnerObjectResponse;
import com.devparadigam.agrade.apiservices.JsonArrayResponse;
import com.devparadigam.agrade.apiservices.JsonObjectResponse;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.ExaminationModel;
import com.devparadigam.agrade.model.response.PaperModel;
import com.devparadigam.agrade.model.response.ResultDetailsResponse;
import com.devparadigam.agrade.model.response.ResultModel;
import com.devparadigam.agrade.model.response.SaveResultModel;
import com.devparadigam.agrade.model.response.TestHistoryModel;
import com.devparadigam.agrade.model.response.TestModel;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestRepository {

    private ApiServices apiServices;
    Disposable  getexamTypeDisposer, getTestDisposer;

    public TestRepository(ApiServices apiServices) {
        this.apiServices = apiServices;
    }

    public MutableLiveData<Resource<List<ExaminationModel>>> getExamType(String token, String exam_type) {
        final MutableLiveData<Resource<List<ExaminationModel>>> examType = new MutableLiveData<>();
        apiServices.getExamType(token, exam_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonArrayResponse<ExaminationModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getexamTypeDisposer = d;
                        examType.setValue(new Resource<List<ExaminationModel>>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonArrayResponse<ExaminationModel> examTypeJsonArrayResponse) {
                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        if (examTypeJsonArrayResponse != null) {
                            if (examTypeJsonArrayResponse.getSuccess()) {
                                if (examTypeJsonArrayResponse.list==null)
                                    examType.setValue(new Resource<List<ExaminationModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                                else if (examTypeJsonArrayResponse.list.isEmpty())
                                    examType.setValue(new Resource<List<ExaminationModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                                else
                                    examType.setValue(new Resource<List<ExaminationModel>>(Status.SUCCESS, examTypeJsonArrayResponse.list, examTypeJsonArrayResponse.getMessage()));
                            } else {
                                examType.setValue(new Resource<List<ExaminationModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                            }
                        } else {
                            examType.setValue(new Resource<List<ExaminationModel>>(Status.ERROR, null, examTypeJsonArrayResponse.getMessage()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        examType.setValue(new Resource<List<ExaminationModel>>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return examType;
    }

    public MutableLiveData<Resource<List<PaperModel>>> getPaper(String token, String exam_id) {
        final MutableLiveData<Resource<List<PaperModel>>> paper = new MutableLiveData<>();
        apiServices.getPaper(token, exam_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonArrayResponse<PaperModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getexamTypeDisposer = d;
                        paper.setValue(new Resource<List<PaperModel>>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonArrayResponse<PaperModel> getPaperJsonArrayResponse) {

                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        if (getPaperJsonArrayResponse != null) {
                            if (getPaperJsonArrayResponse.getSuccess()) {
                                if (getPaperJsonArrayResponse.list==null)
                                    paper.setValue(new Resource<List<PaperModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                                else if (getPaperJsonArrayResponse.list.isEmpty())
                                    paper.setValue(new Resource<List<PaperModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                                else
                                    paper.setValue(new Resource<List<PaperModel>>(Status.SUCCESS, getPaperJsonArrayResponse.list, getPaperJsonArrayResponse.getMessage()));
                            } else {
                                paper.setValue(new Resource<List<PaperModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                            }
                        } else {
                            paper.setValue(new Resource<List<PaperModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                        }



                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        paper.setValue(new Resource<List<PaperModel>>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return paper;
    }

    public MutableLiveData<Resource<TestModel>> getTest(String token, String paper_id, String language) {
        final MutableLiveData<Resource<TestModel>> test = new MutableLiveData<>();
        apiServices.getTest(token, paper_id,language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<InnerObjectResponse<TestModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getTestDisposer = d;
                        test.setValue(new Resource<TestModel>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<InnerObjectResponse<TestModel>> getTestJsonArrayResponse) {
                        if (getTestDisposer != null)
                            getTestDisposer.dispose();
                        if (getTestJsonArrayResponse != null) {
                            if (getTestJsonArrayResponse.getSuccess()) {

                                if (getTestJsonArrayResponse.data==null)
                                    test.setValue(new Resource<TestModel>(Status.ERROR, null, getTestJsonArrayResponse.getMessage()));
                                else
                                if (getTestJsonArrayResponse.data.getPaper()==null)
                                    test.setValue(new Resource<TestModel>(Status.ERROR, null, getTestJsonArrayResponse.getMessage()));
                                else
                                    test.setValue(new Resource<TestModel>(Status.SUCCESS, getTestJsonArrayResponse.data.getPaper(), getTestJsonArrayResponse.getMessage()));

                            } else {
                                test.setValue(new Resource<TestModel>(Status.ERROR, null, getTestJsonArrayResponse.getMessage()));
                            }
                        } else {
                            test.setValue(new Resource<TestModel>(Status.ERROR, null, getTestJsonArrayResponse.getMessage()));
                        }




                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getTestDisposer != null)
                            getTestDisposer.dispose();

                        test.setValue(new Resource<TestModel>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return test;
    }


    public MutableLiveData<Resource<ResultModel>> getResult(String token, String result_id) {
        final MutableLiveData<Resource<ResultModel>> result = new MutableLiveData<>();
        apiServices.getResult(token,result_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<ResultModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getTestDisposer = d;
                        result.setValue(new Resource<ResultModel>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<ResultModel> getResultResponse) {
                        if (getTestDisposer != null)
                            getTestDisposer.dispose();
                        if (getResultResponse != null) {
                            if (getResultResponse.getSuccess()) {
                                if (getResultResponse.data!=null)
                                    result.setValue(new Resource<ResultModel>(Status.SUCCESS, getResultResponse.data, getResultResponse.getMessage()));

                                else {
                                    result.setValue(new Resource<ResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                                }
                            } else {
                                result.setValue(new Resource<ResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                            }
                        } else {
                            result.setValue(new Resource<ResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                        }




                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getTestDisposer != null)
                            getTestDisposer.dispose();

                        result.setValue(new Resource<ResultModel>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return result;
    }

    public MutableLiveData<Resource<ResultDetailsResponse>> get_result_detail(String token, String result_id) {
        final MutableLiveData<Resource<ResultDetailsResponse>> result = new MutableLiveData<>();
        apiServices.get_result_detail(token,result_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<ResultDetailsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getTestDisposer = d;
                        result.setValue(new Resource<ResultDetailsResponse>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<ResultDetailsResponse> getResultResponse) {
                        if (getTestDisposer != null)
                            getTestDisposer.dispose();
                        if (getResultResponse != null) {
                            if (getResultResponse.getSuccess()) {
                                if (getResultResponse.data!=null)
                                    result.setValue(new Resource<ResultDetailsResponse>(Status.SUCCESS, getResultResponse.data, getResultResponse.getMessage()));

                                else {
                                    result.setValue(new Resource<ResultDetailsResponse>(Status.ERROR, null, getResultResponse.getMessage()));
                                }
                            } else {
                                result.setValue(new Resource<ResultDetailsResponse>(Status.ERROR, null, getResultResponse.getMessage()));
                            }
                        } else {
                            result.setValue(new Resource<ResultDetailsResponse>(Status.ERROR, null, getResultResponse.getMessage()));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getTestDisposer != null)
                            getTestDisposer.dispose();

                        result.setValue(new Resource<ResultDetailsResponse>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return result;
    }

    public MutableLiveData<Resource<List<TestHistoryModel>>> testHistory(String token) {
        final MutableLiveData<Resource<List<TestHistoryModel>>> history = new MutableLiveData<>();
        apiServices.testHistory(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonArrayResponse<TestHistoryModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getexamTypeDisposer = d;
                        history.setValue(new Resource<List<TestHistoryModel>>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonArrayResponse<TestHistoryModel> getPaperJsonArrayResponse) {

                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        if (getPaperJsonArrayResponse != null) {
                            if (getPaperJsonArrayResponse.getSuccess()) {
                                if (getPaperJsonArrayResponse.list==null)
                                    history.setValue(new Resource<List<TestHistoryModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                                else if (getPaperJsonArrayResponse.list.isEmpty())
                                    history.setValue(new Resource<List<TestHistoryModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                                else
                                    history.setValue(new Resource<List<TestHistoryModel>>(Status.SUCCESS, getPaperJsonArrayResponse.list, getPaperJsonArrayResponse.getMessage()));
                            } else {
                                history.setValue(new Resource<List<TestHistoryModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                            }
                        } else {
                            history.setValue(new Resource<List<TestHistoryModel>>(Status.ERROR, null, getPaperJsonArrayResponse.getMessage()));
                        }



                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getexamTypeDisposer != null)
                            getexamTypeDisposer.dispose();

                        history.setValue(new Resource<List<TestHistoryModel>>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return history;
    }

    public MutableLiveData<Resource<SaveResultModel>> saveResult(
        String token,
        String paper_id,
        float test_marks,
        String total_time,
        int total_attempt,
        int not_attempt,
        int bookmark,
        int not_visited,
        float total_marks,
        int correct_answer,
        int incorrect_answer,
        TestModel testModel) {
        final MutableLiveData<Resource<SaveResultModel>> result = new MutableLiveData<>();
        apiServices.saveTest(
                 token,
                paper_id,
                test_marks,
                total_time,
                total_attempt,
                not_attempt,
                bookmark,
                not_visited,
                total_marks,
                correct_answer,
                incorrect_answer,
                getMapResponse(testModel)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObjectResponse<SaveResultModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getTestDisposer = d;
                        result.setValue(new Resource<SaveResultModel>(Status.LOADING, null, "Loading"));
                    }

                    @Override
                    public void onSuccess(JsonObjectResponse<SaveResultModel> getResultResponse) {
                        if (getTestDisposer != null)
                            getTestDisposer.dispose();
                        if (getResultResponse != null) {
                            if (getResultResponse.getSuccess()) {
                                if (getResultResponse.data!=null)
                                    result.setValue(new Resource<SaveResultModel>(Status.SUCCESS, getResultResponse.data, getResultResponse.getMessage()));

                                else {
                                    result.setValue(new Resource<SaveResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                                }
                            } else {
                                result.setValue(new Resource<SaveResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                            }
                        } else {
                            result.setValue(new Resource<SaveResultModel>(Status.ERROR, null, getResultResponse.getMessage()));
                        }




                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("check error ", e.getMessage());

                        if (getTestDisposer != null)
                            getTestDisposer.dispose();

                        result.setValue(new Resource<SaveResultModel>(Status.ERROR, null, e.getMessage()));


                    }
                });
        return result;
    }

    String getMapResponse(TestModel testModel){
       String json= new Gson().toJson(testModel);
       Log.e("map to json", json);
       return json;
    }

}
