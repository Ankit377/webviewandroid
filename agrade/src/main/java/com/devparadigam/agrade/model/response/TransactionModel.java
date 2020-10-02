package com.devparadigam.agrade.model.response;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionModel extends BaseModel {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("exam_id")
    @Expose
    private String examId;
    @SerializedName("trans_id")
    @Expose
    private String transId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("insert_id")
    @Expose
    private Integer insertId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getInsertId() {
        return insertId;
    }

    public void setInsertId(Integer insertId) {
        this.insertId = insertId;
    }
}
