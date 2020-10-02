package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.ResultDeatilsBinding;
import com.devparadigam.agrade.model.data.SubjectOperation;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.ResultDetailsResponse;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.model.response.TestSubjectModelNew;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.ui.adapter.ResultDetailsViewPagerAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultDetailsActivity extends BaseActivity implements View.OnClickListener , ViewPager.OnPageChangeListener {

    TestViewModel testViewModel;
    String result_id ;
    ResultDeatilsBinding binding;
    Map<SubjectOperation, List<TestQueModel>> subjectMap = new HashMap<>();
    List<SubjectOperation> subjectList = new ArrayList<>();
    BottomSheetBehavior sheetBehavior;
    SubjectOperation currentSubject;
    List<TestQueModel> currentQuestionList;
    CountDownTimer totaltimer,subjectTimer;
    String time,token,paper_id;
    NumberFormat formater = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_result_details);
        binding.setSubjects(subjectList);
       // setContentView( R.layout.activity_result_details);
        result_id=getIntent().getStringExtra("resultid");
        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(this).getToken();
        setUpTestViewModel();
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.getRoot());
        sheetBehavior.setHideable(true);

        binding.btnSheet.setOnClickListener(this::onClick);
        binding.next.setOnClickListener(this::onClick);
        binding.previous.setOnClickListener(this::onClick);
        binding.bottomSheetLayout.submitTest.setOnClickListener(this::onClick);
        binding.bottomSheetLayout.downArrow.setOnClickListener(this::onClick);

        binding.bottomSheetLayout.subGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSubject(checkedId);
            }
        });

        observerGetResult(token,result_id);

        binding.questionsViewPager.addOnPageChangeListener(this);

    }

    private void setUpTestViewModel() {
        TestFactory factory = new TestFactory(new TestRepository(ApplicationParentClass.getmInstance().getApiServices()));
        try {
            testViewModel = ViewModelProviders.of(this, factory).get(TestViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void observerGetResult(String token, String result_id) {

        testViewModel.get_result_detail(token, result_id).observe(this, new Observer<Resource<ResultDetailsResponse>>() {
            @Override
            public void onChanged(Resource<ResultDetailsResponse> getResultResource) {
                switch (getResultResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (getResultResource.message != null){

                        }
                         //   showSnackBar(binding.getRoot(), getResultResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (getResultResource.message != null)
                            showToast(getResultResource.message);

                        if (getResultResource.data != null) {

                            if (!TextUtils.isEmpty(getResultResource.data.getResponse())){
                               setResponseMap(new Gson().fromJson(getResultResource.data.getResponse(), TestModel.class));
                            }

                        }

                        else {
                           // showSnackBar(binding.getRoot(), getResultResource.message);
                        }

                    }
                }
            }
        });
    }


    void setResponseMap(TestModel testmodel){
        for (TestSubjectModelNew sub : testmodel.getSubjects()) {
            SubjectOperation operation = new SubjectOperation();
            operation.setSubjectId(sub.getSubjectId());
            operation.setSubjects(sub.getSubjects());
            operation.setTime(sub.getTime()+":00");
            subjectList.add(operation);
            subjectMap.put(operation, sub.getQuestion());
        }

        binding.setSubjects(subjectList);

    }


    @Override
    public void onClick(View v) {
        if (v == binding.btnSheet) {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        if (v == binding.bottomSheetLayout.downArrow) {
            if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        if (v == binding.next) {
           /* if (currentSubject.getCurrentPosition()<subjectMap.get(currentSubject).size()) {
                currentSubject.setCurrentPosition(currentSubject.getCurrentPosition() + 1);
                setQuestion(subjectMap.get(currentSubject));
            }*/
            binding.questionsViewPager.setCurrentItem(currentSubject.getCurrentPosition() + 1);
        }

        if (v == binding.previous) {
            /*if (currentSubject.getCurrentPosition()>0) {
                currentSubject.setCurrentPosition(currentSubject.getCurrentPosition() - 1);
                setQuestion(subjectMap.get(currentSubject));
            }*/
            binding.questionsViewPager.setCurrentItem(currentSubject.getCurrentPosition() - 1);
        }
    }

    void setSubject(int checkedId) {

        for (Map.Entry<SubjectOperation, List<TestQueModel>> m : subjectMap.entrySet()) {
            if (m.getKey().getSubjectId().equals("" + checkedId)) {
                currentSubject = m.getKey();
                setBottomQuestionsAdapter(m.getValue());
                setQuestion(m.getValue());
                binding.timeCountSubb.setText(currentSubject.getSubjects());
            }
        }
    }

    void setBottomQuestionsAdapter(List<TestQueModel> list) {
        if (list == null)
            return;

        if (list.isEmpty())
            return;

        GenericListAdapter<TestQueModel> testQListAdapter = new GenericListAdapter<TestQueModel>((ArrayList<TestQueModel>) list,
                R.layout.que_layout_design,
                new GenericListAdapter.OnListItemClickListener<TestQueModel>() {
                    @Override
                    public void onListItemClicked(TestQueModel selItem, @Nullable Object extra, int position) {
                        if (extra != null && extra instanceof View) {
                            switch (((View) extra).getId()) {
                                case R.id.queNumber: {
                                    currentSubject.setCurrentPosition(position);
                                    binding.questionsViewPager.setCurrentItem(position);
                                }
                                break;
                            }
                        }
                    }
                });

        binding.bottomSheetLayout.recyclerBottomSheet.setAdapter(testQListAdapter);

    }

    void setQuestion(List<TestQueModel> list) {
        if (currentSubject.getCurrentPosition() < list.size()) {

            if (list != null)
                if (!list.isEmpty()) {
                    currentQuestionList = list;
                    binding.questionsViewPager.setAdapter(new ResultDetailsViewPagerAdapter(this, currentQuestionList));

                    binding.queInfo.setText("" + (currentSubject.getCurrentPosition() + 1) + " of " + list.size());
                }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentSubject.setCurrentPosition(position);
        binding.queInfo.setText("" + (position + 1) + " of " + currentQuestionList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
