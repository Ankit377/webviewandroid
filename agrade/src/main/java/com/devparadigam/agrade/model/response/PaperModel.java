package com.devparadigam.agrade.model.response;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaperModel extends BaseModel {


    @SerializedName("paper_id")
    @Expose
    private String paperId;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("is_paid")
    @Expose
    private Boolean is_paid;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_status")
    @Expose
    private String isStatus;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("subjects")
    @Expose
    private List<SubjectModel> subjects;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Boolean getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(Boolean is_paid) {
        this.is_paid = is_paid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<SubjectModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectModel> subjects) {
        this.subjects = subjects;
    }

    private boolean isCategoryPaid;

    @Bindable
    public boolean isCategoryPaid() {
        return isCategoryPaid;
    }

    public void setCategoryPaid(boolean categoryPaid) {
        isCategoryPaid = categoryPaid;
        notifyPropertyChanged(BR.categoryPaid);
    }
}
