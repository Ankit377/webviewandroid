package com.devparadigam.agrade.model.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devparadigam.agrade.BR;

public class TestInstructionOperations extends BaseObservable {
    public boolean enableLoading=false;
    public boolean containsData=false;

    String minutes="0";
    Integer questions=0;
    Integer marks=0;
    String lang;


    @Bindable
    public boolean getEnableLoading() {
        return enableLoading;
    }

    public void setEnableLoading(boolean enableLoading) {
        this.enableLoading = enableLoading;
        notifyPropertyChanged(BR.enableLoading);
    }

    @Bindable
    public boolean getContainsData() {
        return containsData;
    }

    public void setContainsData(boolean containsData) {
        this.containsData = containsData;
        notifyPropertyChanged(BR.containsData);
    }


    @Bindable
    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
        notifyPropertyChanged(BR.minutes);
    }

    @Bindable
    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
        notifyPropertyChanged(BR.questions);
    }

    @Bindable
    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
        notifyPropertyChanged(BR.marks);
    }

    @Bindable
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
        notifyPropertyChanged(BR.lang);
    }
}
