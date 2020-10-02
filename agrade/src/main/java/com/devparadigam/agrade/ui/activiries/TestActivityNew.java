package com.devparadigam.agrade.ui.activiries;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.TestBindingNew;
import com.devparadigam.agrade.model.data.SubjectOperation;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.response.SaveResultModel;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.model.response.TestSubjectModelNew;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.devparadigam.agrade.ui.adapter.GenericListAdapter;
import com.devparadigam.agrade.ui.adapter.QuestionViewPagerAdapter;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivityNew extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    TestModel testmodel;
    TestBindingNew binding;
    TestViewModel testViewModel;
    BottomSheetBehavior sheetBehavior;
    List<SubjectOperation> subjectList = new ArrayList<>();
    Map<SubjectOperation, List<TestQueModel>> subjectMap = new HashMap<>();
    SubjectOperation currentSubject;
    List<TestQueModel> currentQuestionList;
    CountDownTimer totaltimer,subjectTimer;
    String time,token,paper_id;
    NumberFormat formater = new DecimalFormat("00");
    // TestQueModel currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_new);
        setUpTestViewModel();

        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.getRoot());
        sheetBehavior.setHideable(true);
        paper_id = getIntent().getStringExtra("paperId");

        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(this).getToken();
        if (ApplicationParentClass.getmInstance().getTestModel() == null) {
            showToast("No data found");
            finish();
        }

        testmodel = ApplicationParentClass.getmInstance().getTestModel();


        for (TestSubjectModelNew sub : testmodel.getSubjects()) {
            SubjectOperation operation = new SubjectOperation();
            operation.setSubjectId(sub.getSubjectId());
            operation.setSubjects(sub.getSubjects());
            operation.setTime(sub.getTime()+":00");
            subjectList.add(operation);
            subjectMap.put(operation, sub.getQuestion());
        }

        binding.setSubjects(subjectList);


        ///////// click listners ////////
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

        binding.questionsViewPager.addOnPageChangeListener(this);

        time=testmodel.getTime()+":00";
       // time="00:00"+":10";
        timer(binding.timeCount);


    }

    void setnextSubject(){

        int currentPosition=0;
        for (int i = 0; i <  binding.bottomSheetLayout.subGroup.getChildCount(); i++) {
            if(currentSubject.getSubjectId().equals(""+((RadioButton)binding.bottomSheetLayout.subGroup.getChildAt(i)).getId()))
            {
                currentPosition=i;
                break;
            }
        }

        if (currentPosition<binding.bottomSheetLayout.subGroup.getChildCount()){
            RadioButton rb= ((RadioButton)binding.bottomSheetLayout.subGroup.getChildAt(currentPosition+1));
            if (rb!=null)
            rb.setChecked(true);
        }

    }

    void setEnableSubjectSwitch(){
        if (!testmodel.getSwitch_able()){

            for (int i = 0; i <  binding.bottomSheetLayout.subGroup.getChildCount(); i++) {
                ((RadioButton)binding.bottomSheetLayout.subGroup.getChildAt(i)).setEnabled(currentSubject.isTimeFinished());
            }

            if (!currentSubject.isTimeFinished()){
                Toast.makeText(TestActivityNew.this, "You can't Switch subject until time is complete", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void timer(TextView textView) {

        String[] tokens = time.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long millisInFuture = secondsToMs+minutesToMs + hoursToMs;

       /* if (totaltimer!=null)
            totaltimer.cancel();*/

        totaltimer = new CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {

               /* time=String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));*/



                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                time=formater.format(hour)+":"+formater.format(min)+":"+formater.format(sec);
                textView.setText(time);

            }

            public void onFinish() {
                showFinishDiaolog(false);
            }
        };
        totaltimer.start();
    }

    void timerSubject(String subjectName,String totaltime) {

        String[] tokens = totaltime.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long millisInFuture = secondsToMs +minutesToMs + hoursToMs;

        if (subjectTimer!=null)
            subjectTimer.cancel();

        subjectTimer = new CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {

                /*int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds%60;
                minutes = minutes % 60;
                hours = hours % 60;
                testmodel.setTime("TIME : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));*/

/*                String time=String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));*/

                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                String time=formater.format(hour)+":"+formater.format(min)+":"+formater.format(sec);
                currentSubject.setTime(time);

                binding.timeCountSubb.setText(time);

                //binding.timeCountSubb.setText(subjectName+" : "+time);

                //  testmodel.setTime(new SimpleDateFormat("hh:mm").format(new Date(millisUntilFinished)));
//              testmodel.setTime(new SimpleDateFormat("hh:mm").format(millisUntilFinished));
            }

            public void onFinish() {

              currentSubject.setTimeFinished(true);
                setnextSubject();
                setEnableSubjectSwitch();

            }
        };

        subjectTimer.start();

    }


    void setSubject(int checkedId) {

        for (Map.Entry<SubjectOperation, List<TestQueModel>> m : subjectMap.entrySet()) {
            if (m.getKey().getSubjectId().equals("" + checkedId)) {
                currentSubject = m.getKey();
                setBottomQuestionsAdapter(m.getValue());
                setQuestion(m.getValue());
                timerSubject(currentSubject.getSubjects(),currentSubject.getTime());
                setEnableSubjectSwitch();
            }
        }

    }

    void setQuestion(List<TestQueModel> list) {
        if (currentSubject.getCurrentPosition() < list.size()) {

            if (list != null)
                if (!list.isEmpty()) {
                    currentQuestionList = list;
                    binding.questionsViewPager.setAdapter(new QuestionViewPagerAdapter(this, currentQuestionList));
                    binding.queInfo.setText("" + (currentSubject.getCurrentPosition() + 1) + " of " + list.size());

                    setHighlightItem(list);
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
                                   // currentSubject.setCurrentPosition(position);
                                    binding.questionsViewPager.setCurrentItem(position);
                                }
                                break;
                            }
                        }
                    }
                });

        binding.bottomSheetLayout.recyclerBottomSheet.setAdapter(testQListAdapter);

    }

    void setHighlightItem(List<TestQueModel> list) {
        if (list == null)
            return;

        if (list.isEmpty())
            return;

        for (int i =0;i<list.size();i++) {
            if (i==currentSubject.getCurrentPosition())
                list.get(i).setCurrentHighlight(true);
            else
                list.get(i).setCurrentHighlight(false);
        }
    }


        @Override
    protected void onStop() {
        super.onStop();
        ApplicationParentClass.getmInstance().setTestModel(null);
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

        if (v == binding.next) {
           /* if (currentSubject.getCurrentPosition()<subjectMap.get(currentSubject).size()) {
                currentSubject.setCurrentPosition(currentSubject.getCurrentPosition() + 1);

            }*/
            binding.questionsViewPager.setCurrentItem(currentSubject.getCurrentPosition() + 1);

        }

        if (v == binding.previous) {
            /*if (currentSubject.getCurrentPosition()>0) {
                currentSubject.setCurrentPosition(currentSubject.getCurrentPosition() - 1);

            }*/
            binding.questionsViewPager.setCurrentItem(currentSubject.getCurrentPosition() - 1);
        }

        if (v==binding.bottomSheetLayout.submitTest){
            showFinishDiaolog(true);
        }

        if (v==binding.bottomSheetLayout.downArrow){
            if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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




    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        // Do nothing //
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentSubject.setCurrentPosition(position);
        binding.queInfo.setText("" + (position + 1) + " of " + currentQuestionList.size());

        setHighlightItem(currentQuestionList);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    void showDialogtimer(TextView textView) {

        String[] tokens = time.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long millisInFuture = secondsToMs+minutesToMs + hoursToMs;

        CountDownTimer totaltimer = new CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {

/*
               String time=String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
*/

                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                String stime=formater.format(hour)+":"+formater.format(min)+":"+formater.format(sec);
                textView.setText(""+stime);

            }

            public void onFinish() {
                textView.setText(time);
            }
        };
        totaltimer.start();
    }


    AlertDialog dialog=null;

    void showFinishDiaolog(boolean isCancaleable) {
        AlertDialog.Builder bulilder = new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_design_layot,null);
        bulilder.setView(view);
        bulilder.setCancelable(isCancaleable);
        TextView time1 = (TextView) view.findViewById(R.id.time);
        TextView answered = (TextView) view.findViewById(R.id.answered);
        TextView notanswered = (TextView) view.findViewById(R.id.notanswered);
        TextView marked = (TextView) view.findViewById(R.id.marked);
        TextView notvisited = (TextView) view.findViewById(R.id.notvisited);

        Button submit = (Button) view.findViewById(R.id.submit);
        //timer(time1);
        showDialogtimer(time1);
        //time1.setText(time + ":" + String.format("%02d", timeCount));
        ImageView dialogButton = (ImageView) view.findViewById(R.id.close);

        dialog=bulilder.create();


        if (isCancaleable==false)
            dialogButton.setVisibility(View.GONE);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFinishing())
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                calculate();
                answered.setText(""+total_attempt);
                notanswered.setText(""+not_attempt);
                marked.setText(""+bookmark);
                notvisited.setText(""+not_visited);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observerSaveTest();
            }
        });

        if (!isFinishing())
        dialog.show();
    }

    ////// Calculations //////////
    float test_marks,total_marks,negativeMarks;
    int total_attempt,not_attempt,
            bookmark,not_visited,
            correct_answer,incorrect_answer;
    String response;

    void calculate(){
        test_marks=0;total_marks=0;
        total_attempt=0;not_attempt=0; bookmark=0;not_visited=0;
        correct_answer=0;incorrect_answer=0;

        enableLoadingBar(true);
        response=testmodel.getTitle();
        for (Map.Entry<SubjectOperation, List<TestQueModel>> m : subjectMap.entrySet()) {

            not_attempt=not_attempt+m.getValue().size();
           // not_visited=not_visited+m.getValue().size();


            for (TestQueModel queModel : m.getValue()){

                total_marks=total_marks+Float.parseFloat(queModel.getPMark());


                if (queModel.getBookmarked())
                    bookmark=bookmark+1;

                if (queModel.getSelectedAns()==null){
                    not_visited=not_visited+1;
                }else {

                    if (queModel.getSelectedAns().equals("a")||queModel.getSelectedAns().equals("b")
                            ||queModel.getSelectedAns().equals("c") || queModel.getSelectedAns().equals("d") ||
                            queModel.getSelectedAns().equals("e")){

                        total_attempt=total_attempt+1;
                        if (queModel.getSelectedAns().equalsIgnoreCase(queModel.getAnswer())) {
                            correct_answer = correct_answer + 1;
                            test_marks=test_marks+Float.parseFloat(queModel.getPMark());
                        }else {
                            incorrect_answer = incorrect_answer + 1;
                            if (queModel.getNMark()!=null)
                            negativeMarks=negativeMarks+Float.parseFloat(queModel.getNMark());
                            // caluate of negative marks here
                           //float nmark= Float.parseFloat(queModel.getNMark());
                        }
                        not_attempt=not_attempt-1;
                    }else {
                        not_attempt=not_attempt-1;
                       // not_visited=not_visited+1;
                    }

                }



            }

            }

        test_marks=test_marks-negativeMarks;
        if (test_marks<0)
            test_marks=0;

        enableLoadingBar(false);

        Log.e("\n test_marks : ",""+test_marks);
        Log.e("\n negativeMarks : ",""+negativeMarks);
            Log.e("\n total_marks : ",""+total_marks);
        Log.e("\n total_attempt : ",""+total_attempt);
        Log.e("\n not_attempt : ",""+not_attempt);
        Log.e("\n bookmark : ",""+bookmark);
        Log.e("\n not_visited : ",""+not_visited);
        Log.e("\n correct_answer : ",""+correct_answer);
        Log.e("\n incorrect_answer : ",""+incorrect_answer);

    }

    void observerSaveTest() {
        //testViewModel.saveResult(token,paper_id,test_marks,total_attempt,not_attempt,bookmark,not_visited,total_marks,total_time,correct_answer,incorrect_answer).observe(this, new Observer<Resource<SaveResultModel>>() {
        testViewModel.saveResult(
                token,
                paper_id,
                test_marks,
                testmodel.getTime(),
                total_attempt,
                not_attempt,
                bookmark,
                not_visited,
                total_marks,
                correct_answer,
                incorrect_answer,
                testmodel
        )
                .observe(this, new Observer<Resource<SaveResultModel>>() {

            public void onChanged(Resource<SaveResultModel> saveResultResponse) {
                switch (saveResultResponse.status){
                    case IDEAL:{ }break;
                    case LOADING:{
                        enableLoadingBar(true);
                    }break;
                    case ERROR:{
                        enableLoadingBar(false);
                        if (saveResultResponse.message!=null)
                            showSnackBar(binding.getRoot(), saveResultResponse.message);
                    }break;
                    case SUCCESS:{
                        enableLoadingBar(false);
                        if (saveResultResponse.message!=null)
                            showToast(saveResultResponse.message);

                        if (saveResultResponse.data!=null) {
                            Intent intent=new Intent(TestActivityNew.this,TestResultActivity.class);
                            intent.putExtra("resultid",""+saveResultResponse.data.getResultId());
                            startActivity(intent);
                            finish();


                        }else{
                            showSnackBar(binding.getRoot(),saveResultResponse.message);
                        }

                    }
                }
            }
        });

    }
}
