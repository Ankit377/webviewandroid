package com.devparadigam.agrade.model.response;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionModel extends BaseModel {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_A")
    @Expose
    private String option_A;
    @SerializedName("option_B")
    @Expose
    private String option_B;
    @SerializedName("option_C")
    @Expose
    private String option_C;
    @SerializedName("option_D")
    @Expose
    private String option_D;
    @SerializedName("correct_answer")
    @Expose
    private String correct_answer;
    @SerializedName("selected_answer")
    @Expose
    private String selected_answer;
    @SerializedName("isBookMarked")
    @Expose
    private boolean isBookMarked;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isCorrect")
    @Expose
    private boolean isCorrect;
    @SerializedName("isAttempt")
    @Expose
    private int isAttempt ;
    @SerializedName("question_number")
    @Expose
    private String question_number;

//    @Bindable
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
       // notifyPropertyChanged(com.devparadigam.agrade.BR.question);
    }

    public String getOption_A() {
        return option_A;
    }

    public void setOption_A(String option_A) {
        this.option_A = option_A;
    }

    public String getOption_B() {
        return option_B;
    }

    public void setOption_B(String option_B) {
        this.option_B = option_B;
    }

    public String getOption_C() {
        return option_C;
    }

    public void setOption_C(String option_C) {
        this.option_C = option_C;
    }

    public String getOption_D() {
        return option_D;
    }

    public void setOption_D(String option_D) {
        this.option_D = option_D;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getSelected_answer() {
        return selected_answer;
    }

    public void setSelected_answer(String selected_answer) {
        this.selected_answer = selected_answer;
    }

    @Bindable
    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
        notifyPropertyChanged(BR.bookMarked);
    }
    @Bindable
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        notifyPropertyChanged(BR.active);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Bindable
    public int getIsAttempt() {
        return isAttempt;
    }

    public void setIsAttempt(int isAttempt) {
        this.isAttempt = isAttempt;
        notifyPropertyChanged(BR.isAttempt);
    }




    public String getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(String question_number) {
        this.question_number = question_number;
    }
}
