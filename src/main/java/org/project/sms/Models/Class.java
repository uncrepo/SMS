package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Class {
    private  StringProperty classId;
    private StringProperty fullName;
    private  StringProperty email;
    private  StringProperty phone;
    private  StringProperty guardian;
    private  StringProperty academicYear;
    private  StringProperty grade;
    private  StringProperty section;


    public Class(String id, String name) {
        this.classId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
    }

    public Class(String id, String name, String email, String guardian, String phone) {
        this.classId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
    }

    public Class(String classId,String academicYear, String grade, String section) {
        this.classId = new SimpleStringProperty(classId);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
    }

    public Class(String id, String name, String email, String guardian, String phone, String academicYear, String grade, String section) {
        this.classId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
    }


    public void setClassId(String studentId) {
        this.classId.set(studentId);
    }

    public void setFullName(String name) {
        this.fullName.set(name);
    }


    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setGuardian(String guardian) {
        this.guardian.set(guardian);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear.set(academicYear);
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getAcademicYear() { return academicYear.get(); }
    public StringProperty academicYearProperty() { return academicYear; }

    public String getGrade() { return grade.get(); }
    public StringProperty gradeProperty() { return grade; }

    public String getSection() { return section.get(); }
    public StringProperty sectionProperty() { return section; }

    public String getClassId() { return classId.get(); }
    public StringProperty classIdProperty() { return classId; }

    public String getFullName() { return fullName.get(); }
    public StringProperty FullNameProperty() { return fullName; }


    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }

    public String getGuardian() { return guardian.get(); }
    public StringProperty guardianProperty() { return guardian; }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
}

