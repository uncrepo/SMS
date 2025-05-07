package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Result {
    private final StringProperty studentId;
    private final StringProperty fullName;
    private final StringProperty attendance;
    private final StringProperty indivAsgn;
    private final StringProperty grpAsgn;
    private final StringProperty midExam;
    private final StringProperty finalExam;
    private final StringProperty total;

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

    public StringProperty idProperty() { return studentId; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty attendanceProperty() { return attendance; }
    public StringProperty indivAsgnProperty() { return indivAsgn; }
    public StringProperty grpAsgnProperty() { return grpAsgn; }
    public StringProperty midExamProperty() { return midExam; }
    public StringProperty finalExamProperty() { return finalExam; }
    public StringProperty totalProperty() { return total; }

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

}
