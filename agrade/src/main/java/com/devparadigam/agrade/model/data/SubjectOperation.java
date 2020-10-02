package com.devparadigam.agrade.model.data;

public class SubjectOperation {
    private String subjects;
    private String subjectId;
    private int currentPosition=0;
    private String time="00:00";
    private boolean isTimeFinished= false;

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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getTime() {
        return time;
       // return  "00:00:10";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTimeFinished() {
        return isTimeFinished;
    }

    public void setTimeFinished(boolean timeFinished) {
        isTimeFinished = timeFinished;
    }
}
