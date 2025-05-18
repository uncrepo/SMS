package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddStudent {
    private  StringProperty studentId;
    private StringProperty fullName;
    private  StringProperty email;
    private  StringProperty password;
    private  StringProperty username;
    private  StringProperty phone;
    private  StringProperty guardian;
    private  StringProperty gender;
    private  StringProperty status;



    public AddStudent(String id, String name) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
    }

    public AddStudent(String id, String name, String email, String guardian, String phone) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
    }

    public AddStudent(String name, String email, String guardian, String phone, String gender, String status) {
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.gender =  new SimpleStringProperty(gender);
        this.status = new SimpleStringProperty(status);
    }


    public AddStudent(String name, String email,String username, String password, String gender,String guardian, String phone) {
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.username = new SimpleStringProperty(username);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.gender = new SimpleStringProperty(gender);
    }




    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
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

//    public void setAcademicYear(String academicYear) {
//        this.academicYear.set(academicYear);
//    }
//
//    public void setGrade(String grade) {
//        this.grade.set(grade);
//    }
//
//    public void setSection(String section) {
//        this.section.set(section);
//    }
//
//    public void setPromotion(String promotion) {
//        this.promotion.set(promotion);
//    }
//
//    public void setAverage(String average) {
//        this.section.set(average);
//    }
//    public String getAcademicYear() { return academicYear.get(); }
//    public StringProperty academicYearProperty() { return academicYear; }
//
//    public String getGrade() { return grade.get(); }
//    public StringProperty gradeProperty() { return grade; }
//
//    public String getSection() { return section.get(); }
//    public StringProperty sectionProperty() { return section; }

    public String getStudentId() { return studentId.get(); }
    public StringProperty studentIdProperty() { return studentId; }

    public String getFullName() { return fullName.get(); }
    public StringProperty FullNameProperty() { return fullName; }


    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }

    public String getGuardian() { return guardian.get(); }
    public StringProperty guardianProperty() { return guardian; }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }

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


}


