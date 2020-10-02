package com.devparadigam.agrade.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.ExaminationModel;
import com.devparadigam.agrade.model.response.PaperModel;
import com.devparadigam.agrade.model.response.ResultDetailsResponse;
import com.devparadigam.agrade.model.response.ResultModel;
import com.devparadigam.agrade.model.response.SaveResultModel;
import com.devparadigam.agrade.model.response.TestHistoryModel;
import com.devparadigam.agrade.model.response.TestModel;

import java.util.List;

public class TestViewModel extends ViewModel {

    TestRepository testRepository;
    LiveData<Resource<TestViewModel>> liveData;


    public TestViewModel(TestRepository testRepository) {
        this.testRepository = testRepository;
        liveData = new MediatorLiveData<>();
        ((MediatorLiveData<Resource<TestViewModel>>) liveData).postValue(new Resource<TestViewModel>(Status.IDEAL, null, ""));
    }


    public MutableLiveData<Resource<List<ExaminationModel>>> getExamType(String token, String exam_type){
        return testRepository.getExamType(token,exam_type);
    }
    public MutableLiveData<Resource<List<PaperModel>>> getPaper(String token, String exam_type){
        return testRepository.getPaper(token,exam_type);
    }
    public MutableLiveData<Resource<TestModel>> getTest(String token, String paperId, String language){
        return testRepository.getTest(token,paperId,language);
    }

    public MutableLiveData<Resource<ResultModel>> getResult(String token, String result_id){
        return testRepository.getResult(token,result_id);
    }

    public MutableLiveData<Resource<ResultDetailsResponse>> get_result_detail(String token, String result_id){
        return testRepository.get_result_detail(token,result_id);
    }

    public MutableLiveData<Resource<List<TestHistoryModel>>> testhistory(String token){
        return testRepository.testHistory(token);
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
            TestModel testmodel
    ){
        return testRepository.saveResult(
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
                testmodel
        );
    }



}
