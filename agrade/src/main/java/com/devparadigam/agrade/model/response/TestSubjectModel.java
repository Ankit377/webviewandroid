package com.devparadigam.agrade.model.response;

import android.os.Parcelable;

import com.devparadigam.agrade.base.BaseModel;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestSubjectModel extends BaseModel implements Parcelable {

    @SerializedName("subjects")
    @Expose
    private String subjects;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("question")
    @Expose
    private List<TestQueModel> question = null;

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<TestQueModel> getQuestion() {
        return question;
    }

    public void setQuestion(List<TestQueModel> question) {
        this.question = question;
    }
}
