package com.devparadigam.agrade.model.response;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveResultModel extends BaseModel {

    @SerializedName("paper_id")
    @Expose
    private String paperId;
    @SerializedName("test_marks")
    @Expose
    private String testMarks;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("total_attempt")
    @Expose
    private String totalAttempt;
    @SerializedName("not_attempt")
    @Expose
    private String notAttempt;
    @SerializedName("bookmark")
    @Expose
    private String bookmark;
    @SerializedName("not_visited")
    @Expose
    private String notVisited;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("incorrect_answer")
    @Expose
    private String incorrectAnswer;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("result_id")
    @Expose
    private Integer resultId;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTestMarks() {
        return testMarks;
    }

    public void setTestMarks(String testMarks) {
        this.testMarks = testMarks;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(String totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public String getNotAttempt() {
        return notAttempt;
    }

    public void setNotAttempt(String notAttempt) {
        this.notAttempt = notAttempt;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getNotVisited() {
        return notVisited;
    }

    public void setNotVisited(String notVisited) {
        this.notVisited = notVisited;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(String incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }
}
