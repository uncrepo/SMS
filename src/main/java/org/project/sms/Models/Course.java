package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private StringProperty courseId;
    private  StringProperty courseTitle;
    private  StringProperty courseCode;
    private  StringProperty creditHour;
    private  StringProperty assignedTeacher;


    public Course(String courseId, String courseTitle, String courseCode, String creditHour) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseCode = new SimpleStringProperty(courseCode);
        this.creditHour = new SimpleStringProperty(creditHour);
    }

    public Course(String courseId, String courseTitle, String courseCode, String creditHour, String assignedTeacher) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseCode = new SimpleStringProperty(courseCode);
        this.creditHour = new SimpleStringProperty(creditHour);
        this.assignedTeacher = new SimpleStringProperty(assignedTeacher);

    }


    public void setCourseId(String courseId) {
        this.courseId.set(courseId);
    }

    public void setAssignedTeacher(String name) {
        this.assignedTeacher.set(name);
    }


    public void setCourseTitle(String courseTitle) {
        this.courseTitle.set(courseTitle);
    }

    public void setCourseCode(String courseCode) {
        this.courseCode.set(courseCode);
    }

    public void setCreditHour(String creditHour) {
        this.creditHour.set(creditHour);
    }

    public String getCourseId() { return courseId.get(); }
    public StringProperty courseIdProperty() { return courseId; }

    public String getAssignedTeacher() { return assignedTeacher.get(); }
    public StringProperty assignedTeacherProperty() { return assignedTeacher; }


    public String getCourseCode() { return courseCode.get(); }
    public StringProperty courseCodeProperty() { return courseCode; }

    public String getCourseTitle() { return courseTitle.get(); }
    public StringProperty courseTitleProperty() { return courseTitle; }

    public String getCreditHour() { return creditHour.get(); }
    public StringProperty creditHourProperty() { return creditHour; }
}
