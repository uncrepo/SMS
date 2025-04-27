package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Teacher {
    private  StringProperty teacherId;
    private  StringProperty department;
    private  StringProperty year;
    private  StringProperty section;
    private  StringProperty fullName;
    private  StringProperty courseCode;
    private  StringProperty email;


    public Teacher(String teacherId, String department,String year,String section,String courseCode) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.department = new SimpleStringProperty(department);
        this.year = new SimpleStringProperty(year);
        this.section = new SimpleStringProperty(section);
        this.courseCode = new SimpleStringProperty(courseCode);
    }

    public Teacher(String teacherId, String email ,String department, String year, String section, String courseCode, String fullName) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.department = new SimpleStringProperty(department);
        this.email = new SimpleStringProperty(email);
        this.year = new SimpleStringProperty(year);
        this.section = new SimpleStringProperty(section);
        this.courseCode = new SimpleStringProperty(courseCode);
        this.fullName = new SimpleStringProperty(fullName);
    }


    public Teacher(String teacherId, String department, String fullName) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.department = new SimpleStringProperty(department);
        this.fullName = new SimpleStringProperty(fullName);
    }


    public String getTeacherId() { return teacherId.get(); }
    public StringProperty teacherIdProperty() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId.set(teacherId); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }
    public StringProperty fullNameProperty() { return fullName; }

    public String getDepartment() {return department.get();}
    public void setDepartment(String department) { this.department.set(department); }
    public StringProperty departmentProperty() {
        return department;
    }

    public String getSection() {
        return section.get();
    }
    public void setSection(String section) { this.section.set(section); }
    public StringProperty sectionProperty() {
        return section;
    }

    public String getYear() {
        return year.get();
    }
    public void setYear(String year) { this.year.set(year); }
    public StringProperty yearProperty() {
        return year;
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() {
        return email;
    }

    public String getCourseCode() {
        return courseCode.get();
    }
    public void setCourseCode(String courseCode) { this.courseCode.set(courseCode); }
    public StringProperty courseCodeProperty() {
        return courseCode;
    }



}
