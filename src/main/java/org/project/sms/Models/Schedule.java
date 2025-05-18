package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Schedule {
    private StringProperty scheduleId;
    private StringProperty gradeId;
    private StringProperty section;
    private StringProperty teacherId;
    private StringProperty courseId;
    private StringProperty dayOfWeek;
    private StringProperty periodNumber;
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty academicYear;

    public Schedule(String scheduleId, String gradeId, String section, String teacherId,
                    String courseId, String dayOfWeek, String periodNumber,
                    String startTime, String endTime, String academicYear) {
        if (gradeId == null || section == null || teacherId == null || courseId == null ||
                dayOfWeek == null || periodNumber == null || academicYear == null) {
            throw new IllegalArgumentException("Required fields cannot be null");
        }

        this.scheduleId = new SimpleStringProperty(scheduleId);
        this.gradeId = new SimpleStringProperty(gradeId);
        this.section = new SimpleStringProperty(section);
        this.teacherId = new SimpleStringProperty(teacherId);
        this.courseId = new SimpleStringProperty(courseId);
        this.dayOfWeek = new SimpleStringProperty(dayOfWeek);
        this.periodNumber = new SimpleStringProperty(periodNumber);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.academicYear = new SimpleStringProperty(academicYear);
    }

    // Getters
    public String getScheduleId() { return scheduleId.get(); }
    public String getGradeId() { return gradeId.get(); }
    public String getSection() { return section.get(); }
    public String getTeacherId() { return teacherId.get(); }
    public String getCourseId() { return courseId.get(); }
    public String getDayOfWeek() { return dayOfWeek.get(); }
    public String getPeriodNumber() { return periodNumber.get(); }
    public String getStartTime() { return startTime.get(); }
    public String getEndTime() { return endTime.get(); }
    public String getAcademicYear() { return academicYear.get(); }

    // Property getters
    public StringProperty scheduleIdProperty() { return scheduleId; }
    public StringProperty gradeIdProperty() { return gradeId; }
    public StringProperty sectionProperty() { return section; }
    public StringProperty teacherIdProperty() { return teacherId; }
    public StringProperty courseIdProperty() { return courseId; }
    public StringProperty dayOfWeekProperty() { return dayOfWeek; }
    public StringProperty periodNumberProperty() { return periodNumber; }
    public StringProperty startTimeProperty() { return startTime; }
    public StringProperty endTimeProperty() { return endTime; }
    public StringProperty academicYearProperty() { return academicYear; }

    // Setters
    public void setScheduleId(String scheduleId) { this.scheduleId.set(scheduleId); }
    public void setGradeId(String gradeId) { this.gradeId.set(gradeId); }
    public void setSection(String section) { this.section.set(section); }
    public void setTeacherId(String teacherId) { this.teacherId.set(teacherId); }
    public void setCourseId(String courseId) { this.courseId.set(courseId); }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek.set(dayOfWeek); }
    public void setPeriodNumber(String periodNumber) { this.periodNumber.set(periodNumber); }
    public void setStartTime(String startTime) { this.startTime.set(startTime); }
    public void setEndTime(String endTime) { this.endTime.set(endTime); }
    public void setAcademicYear(String academicYear) { this.academicYear.set(academicYear); }

    @Override
    public String toString() {
        return String.format("Schedule{grade=%s, section=%s, teacher=%s, course=%s, day=%s, period=%s}",
                gradeId.get(), section.get(), teacherId.get(), courseId.get(), dayOfWeek.get(), periodNumber.get());
    }
}