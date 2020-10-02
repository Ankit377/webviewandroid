package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.InstructionBinding;
import com.devparadigam.agrade.model.data.TestInstructionOperations;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.SubjectModel;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.model.response.TestSubjectModelNew;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.devparadigam.agrade.viewmodels.UserViewModel;

public class TestInstructionActivity extends BaseActivity {

    InstructionBinding binding;
    String paperId="";
    UserViewModel userViewModel;
    TestViewModel testViewModel;
    TestModel testModel;
    String language;
    TestInstructionOperations operations=new TestInstructionOperations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test_instruction);
        binding.setModel(operations);

        paperId=getIntent().getStringExtra("paperid");
        language=getResources().getString(R.string.english);

        setUpTestViewModel();
        observerGetTest(StaticData.TOKEN_TYPE + " " +mPref.getUserProfileDetails(this).getToken());

        binding.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.language.getSelectedItem().toString().equals(getResources().getString(R.string.english))){
                    language=getResources().getString(R.string.english);
                }else {
                    language="Hindi";
                }

                if (!operations.getEnableLoading())
                observerGetTest(StaticData.TOKEN_TYPE + " " +mPref.getUserProfileDetails(TestInstructionActivity.this).getToken());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!operations.containsData){
                    showSnackBar(binding.getRoot(),"Fetching questions, Please wait...");
                }else {
                   // String data= new Gson().toJson(testModel);
                  //  Bundle bundle=new Bundle();
                   // bundle.putParcelable("test",testModel);
                    ApplicationParentClass.getmInstance().setTestModel(testModel);
                    Intent intent = new Intent(TestInstructionActivity.this, TestActivityNew.class);
                    intent.putExtra("paperId",paperId);
                    startActivity(intent);
                    finish();
                }
/*
                Intent intent = new Intent(TestInstructionActivity.this, Test_Activity.class);
//                intent.putExtra("id",id);
                startActivity(intent);
*/
            }
        });
    }


    private void setUpTestViewModel() {
        TestFactory factory = new TestFactory(new TestRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            testViewModel = ViewModelProviders.of(this, factory).get(TestViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void observerGetTest(String token) {
        testViewModel.getTest(token, paperId,language).observe(this, new Observer<Resource<TestModel>>() {
            @Override
            public void onChanged(Resource<TestModel> testDetailsResource) {
                switch (testDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        operations.setEnableLoading(true);
                        operations.setContainsData(false);
                        //enableLoadingBar(true);

                        binding.language.setEnabled(false);
                    }
                    break;
                    case ERROR: {
                        operations.setEnableLoading(false);
                        operations.setContainsData(false);
                        //enableLoadingBar(false);

                        binding.language.setEnabled(true);

                        if (testDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), testDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        //enableLoadingBar(false);
                        operations.setEnableLoading(false);
                        operations.setContainsData(true);
//                        showToast(testDetailsResource.message);




                        binding.language.setEnabled(true);
                        testModel= testDetailsResource.data;

                        int question=0,marks=0;

                        if (testDetailsResource.data.getSubjects().isEmpty())
                        return;

                                for(TestSubjectModelNew it : testDetailsResource.data.getSubjects()){
                            //if (it.question.isNullOrEmpty())
                            // continue;

                            for(TestQueModel t: it.getQuestion()) {
                                question=question+1;

                                try {
                                    int pmarks= Integer.parseInt(t.getPMark());
                                    marks= marks+pmarks;
                                }catch (Exception e){
                                    marks=marks+0;
                                }


                            }
                        }

                                if (testModel.getSwitch_able()){
                                    operations.setLang("Yes");
                                }else {
                                    operations.setLang("No");
                                }
                        operations.setMinutes(testModel.getTime());
                        operations.setMarks(marks);
                        operations.setQuestions(question);

                    }

                }
            }
        });

    }

}
