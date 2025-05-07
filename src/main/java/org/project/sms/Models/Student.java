package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private  StringProperty studentId;
    private StringProperty fullName;
    private  StringProperty email;
    private  StringProperty phone;
    private  StringProperty guardian;

    private  StringProperty section;
    private  StringProperty grade;

    public Student(String id, String name, String email, String guardian, String phone) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.guardian = new SimpleStringProperty(guardian);
        this.section = new SimpleStringProperty(phone);
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
}

