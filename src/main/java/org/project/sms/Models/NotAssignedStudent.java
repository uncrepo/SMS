package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NotAssignedStudent {
    private  StringProperty recordId;
    private StringProperty studentId;
    private StringProperty fullName;
    private  StringProperty comment;
    private  StringProperty grade;
    private  StringProperty average;
    private StringProperty academicYear;



    public NotAssignedStudent(String id, String name, String comment, String grade, String average, String academicYear) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.average = new SimpleStringProperty(average);
        this.grade = new SimpleStringProperty(grade);
        this.comment = new SimpleStringProperty(comment);
        this.academicYear = new SimpleStringProperty(academicYear);
    }

    public String getStudentId() { return studentId.get(); }
    public StringProperty studentIdProperty() { return studentId; }
    public void setStudentId(String gender) { this.studentId.set(gender); }


    public String getFullName() { return fullName.get(); }
    public StringProperty FullNameProperty() { return fullName; }
    public void setFullName(String gender) { this.fullName.set(gender); }


    public String getComment() { return comment.get(); }
    public StringProperty commentProperty() { return comment; }
    public void setComment(String gender) { this.comment.set(gender); }

    public String getAverage() { return average.get(); }
    public StringProperty guardianProperty() { return average; }
    public void setAverage(String gender) { this.average.set(gender); }

    public String getGrade() { return grade.get(); }
    public StringProperty phoneProperty() { return grade; }
    public void setGrade(String gender) { this.grade.set(gender); }


    public String getRecordId() {return recordId.get();}
    public void setRecordId(String gender) { this.recordId.set(gender); }
    public StringProperty genderProperty() {
        return recordId;
    }

    public String getAcademicYear() {return academicYear.get();}
    public void setAcademicYear(String gender) { this.academicYear.set(gender); }
    public StringProperty academicYearProperty() {
        return academicYear;
    }


}
