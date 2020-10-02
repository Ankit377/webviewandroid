package com.devparadigam.agrade.model.response.youtube;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.devparadigam.agrade.base.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestQueModel extends BaseObservable implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("opt1")
    @Expose
    private String opt1;
    @SerializedName("opt2")
    @Expose
    private String opt2;
    @SerializedName("opt3")
    @Expose
    private String opt3;
    @SerializedName("opt4")
    @Expose
    private String opt4;
    @SerializedName("opt5")
    @Expose
    private String opt5;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("p_mark")
    @Expose
    private String pMark;
    @SerializedName("n_mark")
    @Expose
    private String nMark;
    @SerializedName("solution_image")
    @Expose
    private String solutionImage;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_status")
    @Expose
    private String isStatus;

    ////// EXTRA /////////
    private boolean bookmarked=false;
    private String selectedAns;
    private boolean isCurrentHighlight=false;

    protected TestQueModel(Parcel in) {
        id = in.readString();
        question = in.readString();
        subjectId = in.readString();
        opt1 = in.readString();
        opt2 = in.readString();
        opt3 = in.readString();
        opt4 = in.readString();
        opt5 = in.readString();
        answer = in.readString();
        solution = in.readString();
        pMark = in.readString();
        nMark = in.readString();
        solutionImage = in.readString();
        image = in.readString();
        isStatus = in.readString();
        bookmarked=in.readBoolean();
        selectedAns=in.readString();
        isCurrentHighlight=in.readBoolean();
    }

    public static final Creator<TestQueModel> CREATOR = new Creator<TestQueModel>() {
        @Override
        public TestQueModel createFromParcel(Parcel in) {
            return new TestQueModel(in);
        }

        @Override
        public TestQueModel[] newArray(int size) {
            return new TestQueModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getOpt5() {
        return opt5;
    }

    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Bindable
    public String getPMark() {
        return pMark;
    }

    public void setPMark(String pMark) {
        this.pMark = pMark;
        notifyPropertyChanged(BR.pMark);
    }

    public String getNMark() {
        return nMark;
    }

    public void setNMark(String nMark) {
        this.nMark = nMark;
    }

    public String getSolutionImage() {
        return solutionImage;
    }

    public void setSolutionImage(String solutionImage) {
        this.solutionImage = solutionImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
        notifyPropertyChanged(BR.bookmarked);
    }

    @Bindable
    public String getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(String selectedAns) {
        this.selectedAns = selectedAns;
        notifyPropertyChanged(BR.selectedAns);
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Bindable
    public boolean isCurrentHighlight() {
        return isCurrentHighlight;
    }

    public void setCurrentHighlight(boolean currentHighlight) {
        isCurrentHighlight = currentHighlight;
        notifyPropertyChanged(BR.currentHighlight);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(subjectId);
        dest.writeString(opt1);
        dest.writeString(opt2);
        dest.writeString(opt3);
        dest.writeString(opt4);
        dest.writeString(opt5);
        dest.writeString(answer);
        dest.writeString(solution);
        dest.writeString(pMark);
        dest.writeString(nMark);
        dest.writeString(solutionImage);
        dest.writeString(image);
        dest.writeString(isStatus);
        dest.writeBoolean(bookmarked);
        dest.writeString(selectedAns);
        dest.writeBoolean(isCurrentHighlight);
    }
}
