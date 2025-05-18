package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Teacher {
    private  StringProperty teacherId;
    private  StringProperty fullName;
    private  StringProperty email;
    private  StringProperty phone;
    private  StringProperty guardian;
    private  StringProperty academicYear;
    private  StringProperty grade;
    private  StringProperty courseName;
    private  StringProperty section;
    private  StringProperty password;
    private  StringProperty username;
    private  StringProperty gender;
    private  StringProperty status;



    public Teacher(String fullName, String courseCode) {
        this.fullName = new SimpleStringProperty(fullName);
        this.courseName = new SimpleStringProperty(courseCode);
    }

    public Teacher(String teacherId, String fullName ,String email, String phone, String guardian, String academicYear, String grade, String courseName, String section) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.guardian = new SimpleStringProperty(guardian);
        this.grade = new SimpleStringProperty(grade);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.courseName = new SimpleStringProperty(courseName);
        this.section = new SimpleStringProperty(section);
    }

    public Teacher(String id ,String fullName ,String email , String phone, String guardian) {
        this.teacherId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.guardian = new SimpleStringProperty(guardian);
    }
    public Teacher(String fullName ,String email, String username, String password,String gender, String phone, String guardian, String phone2) {
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password =  new SimpleStringProperty(password);
        this.gender = new SimpleStringProperty(gender);
        this.phone = new SimpleStringProperty(phone);
        this.guardian = new SimpleStringProperty(guardian);
    }


    public Teacher(String teacherId, String fullName ,String email, String phone, String guardian, String gender, String status) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.guardian = new SimpleStringProperty(guardian);
        this.status= new SimpleStringProperty(status);
        this.gender = new SimpleStringProperty(gender);
    }

    public Teacher(String fullName ,String email, String phone, String guardian, String gender, String status) {
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.guardian = new SimpleStringProperty(guardian);
        this.gender = new SimpleStringProperty(gender);
        this.status = new SimpleStringProperty(status);
    }

//    public Teacher(String teacherId, String fullName , String academicYear, String grade, String courseName, String section) {
//        this.teacherId = new SimpleStringProperty(teacherId);
//        this.fullName = new SimpleStringProperty(fullName);
//        this.academicYear = new SimpleStringProperty(academicYear);
//        this.grade = new SimpleStringProperty(grade);
//        this.courseName = new SimpleStringProperty(courseName);
//        this.section  = new SimpleStringProperty(section);
//    }

    public Teacher(String teacherId, String grade, String fullName) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.grade = new SimpleStringProperty(grade);
    }


    public String getTeacherId() { return teacherId.get(); }
    public StringProperty teacherIdProperty() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId.set(teacherId); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }
    public StringProperty fullNameProperty() { return fullName; }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {return phone.get();}
    public void setPhone(String phone) { this.phone.set(phone); }
    public StringProperty phoneProperty() {
        return phone;
    }

    public String getGuardian() {return guardian.get();}
    public void setGuardian(String guardian) { this.guardian.set(guardian); }
    public StringProperty guardianProperty() {
        return guardian;
    }

    public String getGrade() {return grade.get();}
    public void setGrade(String grade) { this.grade.set(grade); }
    public StringProperty gradeProperty() {
        return grade;
    }

    public String getSection() {
        return section.get();
    }
    public void setSection(String section) { this.section.set(section); }
    public StringProperty sectionProperty() {
        return section;
    }

    public String getAcademicYear() {return academicYear.get(); }
    public void setAcademicYear(String year) { this.academicYear.set(year); }
    public StringProperty academicYearProperty() {
        return academicYear;
    }

    public String getCourseName() {
        return courseName.get();
    }
    public void setCourseName(String courseCode) { this.courseName.set(courseCode); }
    public StringProperty courseNameProperty() {
        return courseName;
    }

    public String getUsername() {return username.get();}
    public void setUsername(String username) { this.username.set(username); }
    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {return password.get();}
    public void setPassword(String password) { this.password.set(password); }
    public StringProperty passwordProperty() {
        return password;
    }

    public String getGender() {return gender.get();}
    public void setGender(String gender) { this.gender.set(gender); }
    public StringProperty genderProperty() {
        return gender;
    }

    public String getStatus() {return status.get();}
    public void setStatus(String gender) { this.status.set(gender); }
    public StringProperty statusProperty() {
        return status;
    }


}
