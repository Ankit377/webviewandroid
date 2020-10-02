package com.devparadigam.agrade.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.devparadigam.agrade.base.BaseModel;
import com.devparadigam.agrade.model.response.youtube.TestQueModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestSubjectModelNew implements Parcelable {

    @SerializedName("subjects")
    @Expose
    private String subjects;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("question")
    @Expose
    private List<TestQueModel> question = null;

    protected TestSubjectModelNew(Parcel in) {
        subjects = in.readString();
        subjectId = in.readString();
        time = in.readString();
        question = in.createTypedArrayList(TestQueModel.CREATOR);
    }

    public static final Creator<TestSubjectModelNew> CREATOR = new Creator<TestSubjectModelNew>() {
        @Override
        public TestSubjectModelNew createFromParcel(Parcel in) {
            return new TestSubjectModelNew(in);
        }

        @Override
        public TestSubjectModelNew[] newArray(int size) {
            return new TestSubjectModelNew[size];
        }
    };

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjects);
        dest.writeString(subjectId);
        dest.writeTypedList(question);
        dest.writeString(time);
    }
}
