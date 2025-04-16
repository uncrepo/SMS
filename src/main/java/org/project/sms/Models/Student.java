package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private  StringProperty studentId;
    private  StringProperty department;
    private  StringProperty year;
    private  StringProperty section;
    private StringProperty fullName;

    public Student(String id, String department, String section, String year) {
        this.studentId = new SimpleStringProperty(id);
        this.department = new SimpleStringProperty(department);
        this.year = new SimpleStringProperty(year);
        this.section = new SimpleStringProperty(section);
    }

    public Student(String id, String name, String department, String section, String year) {
        this.studentId = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.year = new SimpleStringProperty(year);
        this.section = new SimpleStringProperty(section);
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public void setFullName(String name) {
        this.fullName.set(name);
    }


    public void setSection(String section) {
        this.section.set(section);
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getStudentId() { return studentId.get(); }
    public StringProperty studentIdProperty() { return studentId; }

    public String getFullName() { return fullName.get(); }
    public StringProperty FullNameProperty() { return fullName; }


    public String getDepartment() { return department.get(); }
    public StringProperty departmentProperty() { return department; }

    public String getYear() { return year.get(); }
    public StringProperty yearProperty() { return year; }

    public String getSection() { return section.get(); }
    public StringProperty sectionProperty() { return section; }
}

