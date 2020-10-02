package com.devparadigam.agrade.ui.activiries;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.Test_Binding;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.factories.UserFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.model.requests.Resource;

import com.devparadigam.agrade.model.response.QuestionModel;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.model.response.TestSubjectModel;
import com.devparadigam.agrade.model.response.TestSubjectModelNew;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.devparadigam.agrade.viewmodels.UserViewModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Test_Activity extends BaseActivity {

    Test_Binding binding;
    BottomSheetBehavior sheetBehavior;
    Button button;
    int currentPosition = 0, pagePosition;
    String time,paperId;
    int timeCount;
    TestViewModel testViewModel;
    LinearLayout bottom_sheet;
    RecyclerView recyclerView, subjectRecycler;
    GenericListAdapter<QuestionModel> listAdapter;
    GenericListAdapter<TestSubjectModel> testListAdapter;
    GenericListAdapter<TestQueModel> testQListAdapter;
    ArrayList<QuestionModel> questionsList = new ArrayList<>();
    ArrayList<TestQueModel> testQuestionsList;
    ArrayList<TestSubjectModelNew> testSubjectList;
    CountDownTimer countDownTimer, countDownTimer1;
    UserViewModel userViewModel;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.isHideable();

        recyclerView = findViewById(R.id.recyclerBottomSheet);
//        subjectRecycler = findViewById(R.id.subjectChipsRecycler);
        button = findViewById(R.id.submitTest);
        radioGroup = findViewById(R.id.subGroup);
//        paperId = getIntent().getStringExtra("id");



        setUpViewModel();
        observerGetTest(StaticData.TOKEN_TYPE + " " + "oLFnyWhsXzMBEmG4d0CwkKtOUpu72jgTP5ZYiaQqIAeDHVb8R", "17","English");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = radioGroup.findViewById(checkedId);
                showToast(rb.getText() + " " + checkedId);

                if (testSubjectList!=null)
                    if (!testSubjectList.isEmpty()){
                        for (TestSubjectModelNew sm:testSubjectList){
                            if (sm.getSubjectId().equals(""+checkedId)){

                                testQuestionsList= (ArrayList<TestQueModel>) testSubjectList.get(testSubjectList.indexOf(sm)).getQuestion();
                                setTestQuestion();
                                setSafeAdapter();
                            }
                        }

                    }
            }
        });
        addQuestionstoArrayList("Which command can be used to provide various information on the query plans used by a MongoDB query?");
        addQuestionstoArrayList("Can you run multiple Javascript operations in a single mongod instance?");
        addQuestionstoArrayList("Can one MongoDB operation lock more than one databases? If yes, how?");
        addQuestionstoArrayList("How can you isolate your cursors from intervening with the write operations?");
        addQuestionstoArrayList("What is the role of a profiler in MongoDB? Where does the writes all the data?");
        addQuestionstoArrayList("Which are the two storage engines used by MongoDB?");
        addQuestionstoArrayList("When should we embed one document within another in MongoDB?");
        addQuestionstoArrayList("What are Primary and Secondary Replica sets?");
        addQuestionstoArrayList("What is Replication in MongoDB? Explain.");
        addQuestionstoArrayList("How can you achieve transaction and locking in MongoDB?");
        addQuestionstoArrayList("Mention the command to list all the indexes on a particular collection?");
        binding.pageCounttext.setText("of" + " " + "" + questionsList.size());


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (currentPosition > testQuestionsList.size()) {
                    Toast.makeText(Test_Activity.this, "done", Toast.LENGTH_SHORT).show();
                } else {

                    pagePosition = currentPosition;
                    currentPosition = currentPosition + 1;
//                    setQuestion();
                    binding.examGroup.clearCheck();
                    setTestQuestion();
                }


             /*   if (binding.radiogOption.getCheckedRadioButtonId() == -1) {
//                    questionsList.get(pagePosition).setIsAttempt(2);
                    Toast.makeText(Test_Activity.this, "not attempt", Toast.LENGTH_SHORT).show();
                } else {
                    questionsList.get(pagePosition).setIsAttempt(1);
                    Toast.makeText(Test_Activity.this, "attempted", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        binding.mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testQuestionsList.get(currentPosition).setPMark("1");
                if (testQuestionsList.get(currentPosition).getPMark().equals("1")) {

                    Toast.makeText(Test_Activity.this, "Question " + (currentPosition + 1) + " Bookmarked", Toast.LENGTH_SHORT).show();

                }


            }
        });
        binding.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentPosition <= 0) {
                    Toast.makeText(Test_Activity.this, "done", Toast.LENGTH_SHORT).show();
                } else {
                    currentPosition--;
                    setTestQuestion();
                }

            }
        });

        binding.Cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.examGroup.clearCheck();
//                questionsList.get(currentPosition).setIsAttempt(2);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDiaolog();
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

//        setTestQuestion();

        dismissSheet();
        callBackSheet();


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

    void clearArrayList(){
        if (testQuestionsList!=null)
            testQuestionsList.clear();
    }
    void countDownTimer() {
        countDownTimer = new CountDownTimer(100000, 1000) {

            public void onTick(long millisUntilFinished) {


                time = String.format("%02d", millisUntilFinished / 60000);
                timeCount = (int) ((millisUntilFinished % 60000) / 1000);
                binding.timeCount.setText(time + ":" + String.format("%02d", timeCount));
            }

            public void onFinish() {
                binding.timeCount.setText("done!");
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                alertDiaolog();
            }
        };
        countDownTimer.start();
    }


    void alertDiaolog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_design_layot);
        dialog.setCanceledOnTouchOutside(false);
        TextView time1 = (TextView) dialog.findViewById(R.id.time);
        time1.setText(time + ":" + String.format("%02d", timeCount));
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

 /*   void setQuestion() {
        binding.setQuestion(questionsList.get(currentPosition));
        binding.pageCount.setText("" + (currentPosition + 1));


    }*/

    void setTestQuestion() {
        if (testQuestionsList == null) {
            return;
        }
        if (testQuestionsList.isEmpty()) {
            return;
        }
        if (testQuestionsList != null) {
            if (currentPosition < testQuestionsList.size()) {
                binding.setTestQuestion(testQuestionsList.get(currentPosition));
                binding.pageCount.setText("" + (currentPosition + 1));
            }
        } else {
            Toast.makeText(this, "list is null", Toast.LENGTH_SHORT).show();
        }

    }

    void addQuestionstoArrayList(String question) {
        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion(question);
        questionsList.add(questionModel);

    }

    void setSafeAdapter() {
        if (testQuestionsList == null)
            return;

        if (testQuestionsList.isEmpty())
            return;

        if (testQListAdapter != null) {
            testQListAdapter.notifyDataSetChanged();
        } else {
            testQListAdapter = new GenericListAdapter<TestQueModel>(testQuestionsList,
                    R.layout.que_layout_design,
                    new GenericListAdapter.OnListItemClickListener<TestQueModel>() {
                        @Override
                        public void onListItemClicked(TestQueModel selItem, @Nullable Object extra, int position) {
                            if (extra != null && extra instanceof View) {
                                switch (((View) extra).getId()) {
                                    case R.id.queNumber: {
//                                        binding.setQuestion(questionsList.get(position));
                                        currentPosition = position;
//                                        setQuestion();
                                        setTestQuestion();
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                    }
                                    break;
                                }
                            }
                        }
                    });

            recyclerView.setAdapter(testQListAdapter);
        }
    }

 /*   void setActive() {
        questionsList.get(currentPosition).setActive(true);
//        Toast.makeText(this, "Active", Toast.LENGTH_SHORT).show();

    }
    void setTestActive() {
        if (currentPosition<testQuestionsList.size()) {
            testQuestionsList.get(currentPosition).set_isSelected(true);
//            Toast.makeText(this, "Active", Toast.LENGTH_SHORT).show();
        }
    }*/

    void dismissSheet() {
        // click event for show-dismiss bottom sheet
        binding.btnSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

//                    binding.btnSheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    binding.btnSheet.setText("Expand sheet");
                }
            }
        });

        ImageView imageView = findViewById(R.id.down_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

//                    binding.btnSheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    binding.btnSheet.setText("Expand sheet");
                }
            }
        });
    }


    void callBackSheet() {
        // callback for do something
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        binding.btnSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        binding.btnSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }

        });

    }

    void observerGetTest(String token, String papre_id,String language) {
        testViewModel.getTest(token, papre_id,language).observe(this, new Observer<Resource<TestModel>>() {
            @Override
            public void onChanged(Resource<TestModel> testDetailsResource) {
                switch (testDetailsResource.status) {
                    case IDEAL: {
                    }
                    break;
                    case LOADING: {
                        enableLoadingBar(true);
                    }
                    break;
                    case ERROR: {
                        enableLoadingBar(false);
                        if (testDetailsResource.message != null)
                            showSnackBar(binding.getRoot(), testDetailsResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);

/*
                        if (testDetailsResource.status.equals("success"))
*/
                        showToast(testDetailsResource.message);
//                        testList = (ArrayList<TestSubjectModel>) testDetailsResource.data.getSubjects();
                      //  testQuestionsList = (ArrayList<TestQueModel>) testDetailsResource.data.getSubjects().get(0).getQuestion();
                        if (testDetailsResource.data != null)
//                            time = testDetailsResource.data.getTime();
                            countDownTimer();
//                        binding.pageCounttext.setText(testQuestionsList.size());
                            if (!testDetailsResource.data.getSubjects().isEmpty()) {
                                testSubjectList= (ArrayList<TestSubjectModelNew>) testDetailsResource.data.getSubjects();

                                for (TestSubjectModelNew exm : testSubjectList) {

                                    // can also inflat by xml file // radio button should be parent view
                                    //RadioButton radioButtonView = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null, false);
                                    RadioButton rb = new RadioButton(Test_Activity.this);

                                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(getApplicationContext(), null);
                                    params.setMargins(10, 2, 10, 2);
                                    rb.setLayoutParams(params);
                                    rb.setPadding(35, 2, 35, 2);
                                    rb.setId(Integer.parseInt(exm.getSubjectId()));
                                    rb.setText(exm.getSubjects());
                                    rb.setGravity(Gravity.CENTER);
                                    rb.setBackground(getResources().getDrawable(R.drawable.exam_radio_selector));


                                    ColorStateList colorStateList = new ColorStateList(
                                            new int[][]{

                                                    new int[]{-android.R.attr.state_checked}, //disabled
                                                    new int[]{android.R.attr.state_checked} //enabled
                                            },
                                            new int[] {

                                                    Color.BLACK //disabled
                                                    ,Color.BLUE //enabled

                                            }
                                    );


                            rb.setTextColor(colorStateList);
                                    rb.setButtonDrawable(null);

                                    radioGroup.addView(rb);


                                }

                                if (radioGroup.getCheckedRadioButtonId()==-1){
                                    ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
                                }
                            }
                       /* setTestQuestion();
                        setSafeAdapter();*/
                       /* if (testList == null)
                            return;

                        if (testList.isEmpty())
                            return;

                        if (testListAdapter != null) {
                            testListAdapter.notifyDataSetChanged();
                        } else {
                            testListAdapter = new GenericListAdapter<TestSubjectModel>(testList,
                                    R.layout.subject_chips_layout_design,
                                    new GenericListAdapter.OnListItemClickListener<TestSubjectModel>() {
                                        @Override
                                        public void onListItemClicked(TestSubjectModel selItem, @Nullable Object extra, int position) {
                                            if (extra != null && extra instanceof View) {
                                                switch (((View) extra).getId()) {
                                                    case R.id.radioButton1: {
                                                        currentPosition = position;
                                                        setTestQuestion();
                                                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    });

                            subjectRecycler.setAdapter(testListAdapter);
                        }*/

                    }

                }
            }
        });

    }
}
