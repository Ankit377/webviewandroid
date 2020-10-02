package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.TestHistoryBinding;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.response.TestHistoryModel;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestHistoryActivity extends BaseActivity {

    TestHistoryBinding binding;
    UserViewModel userViewModel;
    TestViewModel testViewModel;
    String token;
    ArrayList<TestHistoryModel> arrayList;
    GenericListAdapter<TestHistoryModel> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test_history);
        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(this).getToken();
        setUpTestViewModel();
        observerTestHistory(token);
    }
    private void setUpViewModel() {
        UserFactory factory = new UserFactory(new UserRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpTestViewModel() {
        TestFactory factory = new TestFactory(new TestRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            testViewModel = ViewModelProviders.of(this, factory).get(TestViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observerTestHistory(String token) {
        testViewModel.testhistory(token).observe(this, new Observer<Resource<List<TestHistoryModel>>>() {
            @Override
            public void onChanged(Resource<List<TestHistoryModel>> testHistoryResource) {
                switch (testHistoryResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (testHistoryResource.message != null)
                            showSnackBar(binding.getRoot(), testHistoryResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (testHistoryResource.status.equals("success"))
                            showToast(testHistoryResource.message);
                        arrayList = (ArrayList<TestHistoryModel>) testHistoryResource.data;


                        listAdapter = new GenericListAdapter<TestHistoryModel>(arrayList,
                                R.layout.testhistory_layoutdesign,
                                new GenericListAdapter.OnListItemClickListener<TestHistoryModel>() {
                            @Override
                            public void onListItemClicked(TestHistoryModel selItem,
                                                          @org.jetbrains.annotations.Nullable Object extra,
                                                          int position) {
                                if (extra != null && extra instanceof View) {
                                    switch (((View) extra).getId()) {
                                        case R.id.card_test_history: {
                                            enableLoadingBar(true);
//                                                      binding.setQuestion(questionsList.get(position));

                                            Intent intent = new Intent(TestHistoryActivity.this, TestResultActivity.class);
                                            intent.putExtra("resultid",selItem.getId());
                                          //  intent.putExtra("resultid","45");
                                            startActivity(intent);
                                            enableLoadingBar(false);

                                        }
                                        break;
                                    }
                                }
                            }
                        });

                        binding.testhistoryRecycler.setAdapter(listAdapter);



                    }
                }
            }
        });

    }

}
