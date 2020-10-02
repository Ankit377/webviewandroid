package com.devparadigam.agrade.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectModel {
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}
