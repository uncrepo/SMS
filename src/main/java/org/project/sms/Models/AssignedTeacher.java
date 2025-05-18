package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssignedTeacher {
    private  StringProperty prevClassId;
    private  StringProperty teacherId;
    private  StringProperty fullName;
    private  StringProperty email;
    private  StringProperty phone;
    private  StringProperty guardian;
    private  StringProperty academicYear;
    private  StringProperty grade;
    private  StringProperty courseName;
    private  StringProperty section;
    private  StringProperty semester;


    public AssignedTeacher(String fullName, String courseCode) {
        this.fullName = new SimpleStringProperty(fullName);
        this.courseName = new SimpleStringProperty(courseCode);
    }

    public AssignedTeacher(String teacherId, String fullName , String email, String phone, String guardian, String academicYear, String grade, String courseName, String section) {
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

    public AssignedTeacher(String teacherId, String fullName , String academicYear,String grade, String courseName, String section) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.courseName = new SimpleStringProperty(courseName);
        this.section  = new SimpleStringProperty(section);
    }

    public AssignedTeacher(String teacherId, String fullName , String academicYear,String grade, String courseName, String section, String semester) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.grade = new SimpleStringProperty(grade);
        this.courseName = new SimpleStringProperty(courseName);
        this.section  = new SimpleStringProperty(section);
        this.semester = new SimpleStringProperty(semester);

    }

    public AssignedTeacher(String teacherId, String grade, String fullName) {
        this.teacherId = new SimpleStringProperty(teacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.grade = new SimpleStringProperty(grade);
    }

    public AssignedTeacher(String selectedTeacherId, String fullName, String prevClassId, String academicYear, String grade, String section, String course, String semester) {
        this.teacherId=new SimpleStringProperty(selectedTeacherId);
        this.fullName = new SimpleStringProperty(fullName);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.section = new SimpleStringProperty(section);
        this.grade = new SimpleStringProperty(grade);
        this.courseName = new SimpleStringProperty(course);
        this.prevClassId = new SimpleStringProperty(prevClassId);
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
    public void setPhone(String department) { this.phone.set(department); }
    public StringProperty phoneProperty() {
        return phone;
    }

    public String getGuardian() {return guardian.get();}
    public void setGuardian(String department) { this.guardian.set(department); }
    public StringProperty guardianProperty() {
        return guardian;
    }

    public String getGrade() {return grade.get();}
    public void setGrade(String department) { this.grade.set(department); }
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

    public String getPrevClassId() {
        return prevClassId.get();
    }
    public void setPrevClassId(String prevClassId) { this.prevClassId.set(prevClassId); }
    public StringProperty prevClassIdProperty() {
        return prevClassId;
    }

    public String getSemester() {
        return semester.get();
    }
    public void setSemester(String courseCode) { this.semester.set(courseCode); }
    public StringProperty semesterProperty() {
        return semester;
    }



}
