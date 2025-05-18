package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private  StringProperty studentId;
    private StringProperty fullName;
    private  StringProperty email;
    private  StringProperty phone;
    private  StringProperty guardian;
    private  StringProperty academicYear;
    private  StringProperty grade;
    private  StringProperty section;
    private  StringProperty promotion;
    private  StringProperty average;
    private  StringProperty gender;
    private  StringProperty status;
    private  StringProperty role;
    private  StringProperty password;
    private  StringProperty username;



    public Student(String id, String name) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
    }

    public Student(String id, String name, String email, String guardian, String phone) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
    }

    public Student(String id, String name, String email, String guardian, String phone, String gender, String status) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.gender =  new SimpleStringProperty(gender);
        this.status = new SimpleStringProperty(status);
    }

    public Student(String id, String name, String email, String guardian, String phone, String gender,
                   String academicYear, String grade, String section, String role, String status) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.gender =  new SimpleStringProperty(gender);
        this.status = new SimpleStringProperty(status);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.role = new SimpleStringProperty(role);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
    }

    public Student(String assignedId, String name, String academicYear, String grade, String section, String studentId) {
        this.studentId = new SimpleStringProperty(assignedId);
        this.fullName = new SimpleStringProperty(name);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
    }

    public Student(String id, String name, String email, String guardian, String phone, String academicYear, String grade, String section) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.phone = new SimpleStringProperty(phone);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
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

    public void setAcademicYear(String academicYear) {
        this.academicYear.set(academicYear);
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public void setPromotion(String promotion) {
        this.promotion.set(promotion);
    }

    public void setAverage(String average) {
        this.section.set(average);
    }
    public String getAcademicYear() { return academicYear.get(); }
    public StringProperty academicYearProperty() { return academicYear; }

    public String getGrade() { return grade.get(); }
    public StringProperty gradeProperty() { return grade; }

    public String getSection() { return section.get(); }
    public StringProperty sectionProperty() { return section; }

    public String getStudentId() { return studentId.get(); }
    public StringProperty studentIdProperty() { return studentId; }

    public String getFullName() { return fullName.get(); }
    public StringProperty fullNameProperty() { return fullName; }


    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }

    public String getGuardian() { return guardian.get(); }
    public StringProperty guardianProperty() { return guardian; }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }

    public String getPromotion() { return promotion.get(); }
    public StringProperty promotionProperty() { return promotion; }

    public String getAverage() { return average.get(); }
    public StringProperty averageProperty() { return average; }


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

    public String getRole() {return role.get();}
    public void setRole(String role) { this.role.set(role); }
    public StringProperty roleProperty() {
        return role;
    }


}

