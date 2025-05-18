package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Result {
    private  StringProperty studentId;
    private  StringProperty fullName;
    private  StringProperty attendance;
    private  StringProperty indivAsgn;
    private  StringProperty grpAsgn;
    private  StringProperty midExam;
    private  StringProperty finalExam;
    private  StringProperty total;
    private  StringProperty courseName;
    private  StringProperty average;
    private  StringProperty comment;


    public Result(String id, String fullName,String average,String comment) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.average = new SimpleStringProperty(average);
        this.comment = new SimpleStringProperty(comment);
    }


    public Result(String id, String fullName,String attendance,String indivAsgn, String grpAsgn,String midExam,String finalExam, String total) {
         this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.attendance = new SimpleStringProperty(attendance);
        this.indivAsgn = new SimpleStringProperty(indivAsgn);
        this.grpAsgn = new SimpleStringProperty(grpAsgn);
        this.midExam = new SimpleStringProperty(midExam);
        this.finalExam = new SimpleStringProperty(finalExam);
        this.total = new SimpleStringProperty(total);
    }

    public Result(String courseName,String attendance,String indivAsgn, String grpAsgn,String midExam,String finalExam, String total) {
        this.courseName = new SimpleStringProperty(courseName);
        this.attendance = new SimpleStringProperty(attendance);
        this.indivAsgn = new SimpleStringProperty(indivAsgn);
        this.grpAsgn = new SimpleStringProperty(grpAsgn);
        this.midExam = new SimpleStringProperty(midExam);
        this.finalExam = new SimpleStringProperty(finalExam);
        this.total = new SimpleStringProperty(total);
    }

    public StringProperty idProperty() { return studentId; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty attendanceProperty() { return attendance; }
    public StringProperty indivAsgnProperty() { return indivAsgn; }
    public StringProperty grpAsgnProperty() { return grpAsgn; }
    public StringProperty midExamProperty() { return midExam; }
    public StringProperty finalExamProperty() { return finalExam; }
    public StringProperty totalProperty() { return total; }
    public StringProperty commentProperty() { return comment; }
    public StringProperty averageProperty() { return average; }


    public String getStudentId() { return studentId.get(); }
    public void setStudentId(String id) { this.studentId.set(id); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }

    public String getAttendance() { return attendance.get(); }
    public void setAttendance(String userId) { this.attendance.set(userId); }

    public String getIndivAsgn() { return indivAsgn.get(); }
    public void setIndivAsgn(String userId) { this.indivAsgn.set(userId); }

    public String getGrpAsgn() { return grpAsgn.get(); }
    public void setGrpAsgn(String userId) { this.grpAsgn.set(userId); }

    public String getmidExam() { return midExam.get(); }
    public void setMidExam(String userId) { this.midExam.set(userId); }

    public String getFinalExam() { return finalExam.get(); }
    public void setFinalExam(String userId) { this.finalExam.set(userId); }

    public String getTotal() { return total.get(); }
    public void setTotal(String userId) { this.total.set(userId); }

    public String getCourseName() { return courseName.get(); }
    public StringProperty courseNameProperty() { return courseName; }

    public String getComment() { return comment.get(); }
    public void setComment(String id) { this.comment.set(id); }

    public String getAverage() { return average.get(); }
    public void setAverage(String fullName) { this.average.set(fullName); }


}
