package com.devparadigam.agrade.ui.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseFragment;
import com.devparadigam.agrade.databinding.HomeFragBinding;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.ExaminationModel;
import com.devparadigam.agrade.model.response.PaperModel;
import com.devparadigam.agrade.ui.activiries.PaymentActivity;
import com.devparadigam.agrade.ui.activiries.TestInstructionActivity;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    HomeFragBinding binding;
    UserViewModel userViewModel;
    TestViewModel testViewModel;
    GenericListAdapter<PaperModel> paperListAdapter;

    ArrayList<ExaminationModel> examinationArrayList=new ArrayList<>();
    ArrayList<PaperModel> paperArrayList=new ArrayList<>();
    //String[] type = {"Banking", "ssc", "Railway"};

    String examId = "", exam_id = "",price="",title="";
    public static final int REQUEST_CODE = 106;

    boolean isCategoryPaid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = HomeFragBinding.inflate(inflater, container, false);
        binding.setUser(mPref.getUserProfileDetails(getActivity()));

        setUpTestViewModel();

        clearExamList();
        examId = getArguments().getString("examId");

        //binding.examGroup.removeAllViews();

        /*binding.typeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_layout, type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.typeSpinner.setAdapter(aa);*/



//        examId = getActivity().getIntent().getStringExtra("parentcategory");
//        observerExamType(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), examId);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        observerExamType(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), examId);

    }

    void getExaminationDetails(int checkedId){
        if (examinationArrayList==null)
            return;
        if (examinationArrayList.isEmpty())
            return;

        for (ExaminationModel model :examinationArrayList){
            if (Integer.parseInt(model.getId())==checkedId){
             //   examId=model.getId();
                price=model.getPrice();
                title=model.getTitle();
                isCategoryPaid = model.isPaid();
                binding.setCatPaid(isCategoryPaid);
                exam_id = model.getId();
            }
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


    void observerExamType(String token, String exam_type) {
        testViewModel.getExamType(token, exam_type).observe(this, new Observer<Resource<List<ExaminationModel>>>() {
            @Override
            public void onChanged(Resource<List<ExaminationModel>> examDetailsResource) {
                switch (examDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (examDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), examDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (examDetailsResource.status.equals("success"))

                            showToast(examDetailsResource.message);

                                examinationArrayList.clear();
                                examinationArrayList = (ArrayList<ExaminationModel>) examDetailsResource.data;
                        if (examDetailsResource.data != null)
                            if (!examDetailsResource.data.isEmpty()) {
                                binding.examGroup.clearCheck();
                                binding.examGroup.removeAllViews();
                                for (ExaminationModel exm : examDetailsResource.data) {
                                    // can also inflat by xml file // radio button should be parent view
                                    //RadioButton radioButtonView = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null, false);
                                    RadioButton rb = new RadioButton(getActivity());
                                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(getActivity(), null);
                                    params.setMargins(10, 2, 10, 2);
                                    rb.setLayoutParams(params);
                                    rb.setPadding(35, 2, 35, 2);
                                    rb.setId(Integer.parseInt(exm.getId()));
                                    rb.setText(exm.getTitle());
                                    rb.setGravity(Gravity.CENTER);
                                    rb.setBackground(getResources().getDrawable(R.drawable.exam_radio_selector));


                                    ColorStateList colorStateList = new ColorStateList(
                                            new int[][]{

                                                    new int[]{-android.R.attr.state_checked}, //disabled
                                                    new int[]{android.R.attr.state_checked} //enabled
                                            },
                                            new int[]{

                                                    Color.BLACK //disabled
                                                    , Color.BLUE //enabled

                                            }
                                    );


                                    rb.setTextColor(colorStateList);
                                    rb.setButtonDrawable(null);

                                    binding.examGroup.addView(rb);
                                    /*if (!exm.getPrice().equalsIgnoreCase("")) {
                                        isPaid = true;
                                    }*/
                                }

                                binding.examGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        // RadioButton rb=binding.examGroup.findViewById(checkedId);
                                        // showToast(rb.getText()+" "+checkedId);
                                        getExaminationDetails(checkedId);
                                        observerGetPaper(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), "" + checkedId);
                                        // showToast(""+checkedId);

                                    }
                                });

                                if (binding.examGroup.getCheckedRadioButtonId() == -1) {
                                    ((RadioButton) binding.examGroup.getChildAt(0)).setChecked(true);
                                }
                            }

                       /* if (examinationlistAdapter != null) {
                            examinationlistAdapter.notifyDataSetChanged();
                        } else {
                            examinationlistAdapter = new GenericListAdapter<ExaminationModel>(examinationArrayList, R.layout.chips_layout_design, new GenericListAdapter.OnListItemClickListener<ExaminationModel>() {
                                @Override
                                public void onListItemClicked(ExaminationModel selItem, @org.jetbrains.annotations.Nullable Object extra, int position) {
                                    if (extra != null && extra instanceof View) {
                                        switch (((View) extra).getId()) {
                                            case R.id.radioButton1: {
                                                observerGetPaper(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), "7");
                                            }
                                            break;
                                        }
                                    }
                                }
                            });
                            binding.recyclerViewChips.setAdapter(examinationlistAdapter);
                        }*/
                    }
                }
            }
        });

    }

    void observerGetPaper(String token, String exam_id) {
        testViewModel.getPaper(token, exam_id).observe(this, new Observer<Resource<List<PaperModel>>>() {
            @Override
            public void onChanged(Resource<List<PaperModel>> paperDetailsResource) {
                switch (paperDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (paperDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), paperDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (paperDetailsResource.status.equals("success"))
                            showToast(paperDetailsResource.message);
                        paperArrayList.clear();
                        paperArrayList = (ArrayList<PaperModel>) paperDetailsResource.data;
                      /*  for (PaperModel paperModel : paperArrayList){
                            id = paperModel.getPaperId();
                        }*/


                        paperListAdapter = new GenericListAdapter<PaperModel>(paperArrayList, R.layout.examlayoutdesign, new GenericListAdapter.OnListItemClickListener<PaperModel>() {
                            @Override
                            public void onListItemClicked(PaperModel selItem, @org.jetbrains.annotations.Nullable Object extra, int position) {
                                if (extra != null && extra instanceof View) {
                                    switch (((View) extra).getId()) {
                                        case R.id.btn_subscribe: {
//                                                enableLoadingBar(true);
//                                                      binding.setQuestion(questionsList.get(position));



                                            if (isCategoryPaid){
                                                Intent intent = new Intent(getActivity(), TestInstructionActivity.class);
                                                intent.putExtra("paperid", selItem.getPaperId());
                                                startActivity(intent);
                                                enableLoadingBar(false);
                                            }else{
                                                if (selItem.getIs_paid()) {

                                                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                                                    intent.putExtra("price", price);
                                                    intent.putExtra("productName", title);
                                                    intent.putExtra("paperId", selItem.getPaperId());
                                                    intent.putExtra("exam_id",exam_id);
                                                    startActivityForResult(intent,REQUEST_CODE);


                                                } else {
                                                    Intent intent = new Intent(getActivity(), TestInstructionActivity.class);
                                                    intent.putExtra("paperid", selItem.getPaperId());
                                                    startActivity(intent);
                                                    enableLoadingBar(false);
                                                }
                                            }

                                        }
                                        break;
                                    }
                                }
                            }
                        });


                       // binding.homeRecycler.setAdapter(paperListAdapter);
                        setAdapter();


                    }
                }
            }
        });

    }

    void setAdapter(){
        if (paperArrayList==null)
            return;
        if (paperArrayList.isEmpty())
            return;
        if (paperListAdapter==null)
            return;

        binding.homeRecycler.setAdapter(paperListAdapter);

        if (isCategoryPaid){
            paperListAdapter.notifyDataSetChanged();
        }else {
            PaperModel paperModel0=null,paperModel1=null;
            paperModel0=paperArrayList.get(0);

            if (paperArrayList.size()>1) {
                paperModel1 = paperArrayList.get(1);
            }

            if (paperModel0!=null)
                paperArrayList.clear();
            paperArrayList.add(paperModel0);

            if (paperModel1!=null) {
                paperModel1.setCategoryPaid(true);
                paperArrayList.add(paperModel1);
            }

            paperListAdapter.notifyDataSetChanged();



        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       /* rb = getActivity().findViewById(R.id.examGroup);
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
        clearExamList();
        if (item.equals("Banking")) {
            examId = "8";
            binding.examGroup.removeAllViews();
            observerExamType(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), examId);

        } else if (item.equals("ssc")) {
            examId = "12";
            binding.examGroup.removeAllViews();
            observerExamType(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), examId);

        } else {
            examId = "13";
            binding.examGroup.removeAllViews();
            observerExamType(StaticData.TOKEN_TYPE + " " + mPref.getToken(getActivity()), examId);

        }*/

    }

    private void clearExamList() {
        if (paperArrayList != null)
            paperArrayList.clear();


    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE==requestCode && resultCode == RESULT_OK) {

            if (data.getBooleanExtra("payment",false)){
                String paperId = data.getStringExtra("paperId");

                isCategoryPaid=true;
                binding.setCatPaid(true);

                Intent intent = new Intent(getActivity(), TestInstructionActivity.class);
                intent.putExtra("paperid", paperId);
                startActivity(intent);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
