package com.devparadigam.agrade.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestModel implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("paper_status")
    @Expose
    private String paperStatus;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("is_status")
    @Expose
    private String isStatus;
    @SerializedName("switch_able")
    @Expose
    private Boolean switch_able;
    @SerializedName("subjects")
    @Expose
    private List<TestSubjectModelNew> subjects = null;

    protected TestModel(Parcel in) {
        title = in.readString();
        paperStatus = in.readString();
        time = in.readString();
        featuredImage = in.readString();
        isStatus = in.readString();
        switch_able = in.readBoolean();
      //  getsubejctlist(in);
        in.readTypedList(subjects,TestSubjectModelNew.CREATOR);
    }


    List<TestSubjectModelNew> getsubejctlist(Parcel in){
        in.readList(subjects,TestSubjectModel.class.getClassLoader());
        return subjects;
    }

    public static final Creator<TestModel> CREATOR = new Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel in) {
            return new TestModel(in);
        }

        @Override
        public TestModel[] newArray(int size) {
            return new TestModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(String paperStatus) {
        this.paperStatus = paperStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public Boolean getSwitch_able() {
        return switch_able;
    }

    public void setSwitch_able(Boolean switch_able) {
        this.switch_able = switch_able;
    }

    public List<TestSubjectModelNew> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TestSubjectModelNew> subjects) {
        this.subjects = subjects;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(paperStatus);
        dest.writeString(time);
        dest.writeString(featuredImage);
        dest.writeString(isStatus);
        dest.writeTypedList(subjects);
        dest.writeBoolean(switch_able);
    }
}
