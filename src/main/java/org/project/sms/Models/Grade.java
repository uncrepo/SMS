package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Grade {
    private StringProperty grade;


    public Grade(String grade) {
        this.grade = new SimpleStringProperty(grade);
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

//    @Override
//    public String toString() {
//        return this.grade;
//    }
//
    public String getGrade() { return grade.get(); }
    public StringProperty gradeProperty() { return grade; }

}
