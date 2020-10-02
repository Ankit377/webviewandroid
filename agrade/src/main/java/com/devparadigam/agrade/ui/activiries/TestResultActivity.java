package com.devparadigam.agrade.ui.activiries;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.devparadigam.agrade.ApplicationParentClass;
import com.devparadigam.agrade.R;
import com.devparadigam.agrade.base.BaseActivity;
import com.devparadigam.agrade.databinding.TestResultBinding;
import com.devparadigam.agrade.model.factories.TestFactory;
import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.model.response.ResultModel;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.utils.StaticData;
import com.devparadigam.agrade.viewmodels.TestViewModel;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class TestResultActivity extends BaseActivity implements OnChartValueSelectedListener {

    TestResultBinding binding;
    TestViewModel testViewModel;
    String token,result_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_test_result);
        token = StaticData.TOKEN_TYPE+" "+mPref.getUserProfileDetails(this).getToken();
        result_id=getIntent().getStringExtra("resultid");
      //  result_id="43";
        setUpTestViewModel();
        observerGetResult(token,result_id);



        binding.piechart.getDescription().setEnabled(false);
        binding.piechart.setExtraOffsets(5, 10, 5, 5);
        binding.piechart.setDragDecelerationFrictionCoef(0.95f);
        binding.piechart.setCenterTextTypeface(tfLight);
        binding.piechart.setCenterText(generateCenterSpannableText());
        binding.piechart.setDrawHoleEnabled(true);
        binding.piechart.setHoleColor(Color.WHITE);
        binding.piechart.setTransparentCircleColor(Color.WHITE);
        binding.piechart.setTransparentCircleAlpha(110);
        binding.piechart.setHoleRadius(58f);
        binding.piechart.setTransparentCircleRadius(61f);
        binding.piechart.setDrawCenterText(true);
        binding.piechart.setRotationAngle(0);
        // enable rotation of the binding.piechart by touch
        binding.piechart.setRotationEnabled(true);
        binding.piechart.setHighlightPerTapEnabled(true);
        binding.piechart.setOnChartValueSelectedListener(this);
        binding.piechart.animateXY(1400, 1400);
        // binding.piechart.spin(2000, 0, 360);

        Legend l = binding.piechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
      /*  binding.piechart.setEntryLabelColor(Color.WHITE);
        binding.piechart.setEntryLabelTypeface(tfRegular);
        binding.piechart.setEntryLabelTextSize(12f);*/

      binding.details.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(TestResultActivity.this,ResultDetailsActivity.class);
              intent.putExtra("resultid",result_id);
              startActivity(intent);
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


    void observerGetResult(String token, String result_id) {

        testViewModel.getResult(token, result_id).observe(this, new Observer<Resource<ResultModel>>() {
            @Override
            public void onChanged(Resource<ResultModel> getResultResource) {
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
                        if (getResultResource.message != null)
                            showSnackBar(binding.getRoot(), getResultResource.message);
                    }
                    break;
                    case SUCCESS: {
                        enableLoadingBar(false);
                        if (getResultResource.message != null)
                            showToast(getResultResource.message);

                        if (getResultResource.data != null) {
                            ResultModel resultModel = getResultResource.data;
                            binding.setTestResult(resultModel);
                            float marks = Float.parseFloat(getResultResource.data.getMarks());
                            float correct_answer = Float.parseFloat(getResultResource.data.getCorrectAnswer());
                            float incorrect_answer = Float.parseFloat(getResultResource.data.getIncorrectAnswer());
                            float notVisited = Float.parseFloat(getResultResource.data.getNotVisited());
                            float attempted = Float.parseFloat(getResultResource.data.getTotalAttempt());
                            float notAttempted = Float.parseFloat(getResultResource.data.getNotAttempt());
                            float totalMarks = Float.parseFloat(getResultResource.data.getTotalMarks());
                            float percentage = marks / 100 * totalMarks;

                            ArrayList<PieEntry> entries = new ArrayList<>();
                            entries.add(new PieEntry(marks, 0));
                            entries.add(new PieEntry(correct_answer, 1));
                            entries.add(new PieEntry(incorrect_answer, 2));




                            PieDataSet dataSet = new PieDataSet(entries, "");

                            dataSet.setDrawIcons(false);

                            dataSet.setSliceSpace(3f);
                            dataSet.setIconsOffset(new MPPointF(0, 40));
                            dataSet.setSelectionShift(6f);

//                            ArrayList<Integer> colors = new ArrayList<>();

                           /* colors.add(R.color.colorPrimary);
                            colors.add(R.color.red);
                            colors.add(R.color.yellow_400);*/

//                            dataSet.setColors(colors);


/*

                            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                                colors.add(c);
*/



//                            colors.add(ColorTemplate.getHoloBlue());
//                            dataSet.setColors(colors);
                            //dataSet.setSelectionShift(0f);
                            final int[] MY_COLORS = {Color.rgb(103, 58, 183), Color.rgb(67, 153, 38), Color.rgb(234, 56, 49)};
                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            for(int c: MY_COLORS) colors.add(c);

                          dataSet.setColors(colors);

                            PieData data = new PieData(dataSet);

                            data.setValueTextSize(11f);
                            data.setValueTextColor(Color.WHITE);
                            data.setValueTypeface(tfLight);
                            binding.piechart.setData(data);

                            // undo all highlights
                            binding.piechart.highlightValues(null);

                            binding.piechart.invalidate();


                            binding.progressView.setProgress(percentage);
                            binding.progressView.setLabelText("Percentage"+percentage+"%");
                            binding.progressView.setAutoAnimate(true);
                          /*  binding.progressView.setMax(100);
                            binding.progressView.setMin(0);*/

                        }

                         else {
                            showSnackBar(binding.getRoot(), getResultResource.message);
                        }

                    }
                }
            }
        });
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Test Series");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 11, 0);

        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private void postProgress(int progress) {
                     String strProgress = String.valueOf(progress) + " %";
                     String max = String.valueOf(100) + " %";
                     String min = String.valueOf(0) + " %";
                   //  binding.pbHorizontal.setProgress(progress);




                     /* update secondary progress of horizontal progress bar */
                     if(progress == 0) {
                         //   binding.pbHorizontal.setSecondaryProgress(0);
                        } else {
                       //     binding.pbHorizontal.setSecondaryProgress(progress);
                         }
                    //  binding.tvProgressHorizontal.setText(strProgress);

                  }



}
