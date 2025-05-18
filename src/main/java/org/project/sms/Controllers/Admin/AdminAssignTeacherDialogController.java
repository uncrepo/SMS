package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.project.sms.Models.AssignedTeacher;
import org.project.sms.Models.Model;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.ClassDAO;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.GradeDAO;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminAssignTeacherDialogController implements Initializable {
    public TextField selectedTeacherNameField;
    public TextField selectedTeacherIdField;
    public TextField prevClassIdField;
    public ComboBox<String> EditAcademicYearComboBox;
    public ComboBox<String> EditSemesterComboBox;
    public ComboBox<String> EditGradeComboBox;
    public ComboBox<String> EditCourseComboBox;
    public ComboBox<String> EditSectionComboBox;
    public ComboBox<String> classAdvisorComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
    }

    public AssignedTeacher getEditAssignedTeacherValues() {
        String fullName = selectedTeacherNameField.getText();
        String selectedTeacherId=selectedTeacherIdField.getText();
        String prevClassId = prevClassIdField.getText();
        String academicYear = EditAcademicYearComboBox.getValue();
        String grade = EditGradeComboBox.getValue();
        String course = EditCourseComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String semester = EditSemesterComboBox.getValue();

        return new AssignedTeacher(selectedTeacherId,fullName,prevClassId,academicYear,grade, section,course, semester);
    }

    public void setAssignedTeacherForEdit(AssignedTeacher editTeacher) {
        selectedTeacherIdField.setText(editTeacher.getTeacherId());
        selectedTeacherNameField.setText(editTeacher.getFullName());
        EditAcademicYearComboBox.setValue(editTeacher.getAcademicYear());
        EditSectionComboBox.setValue(editTeacher.getSection());
        prevClassIdField.setText(ClassDAO.getClassID(editTeacher.getGrade(),editTeacher.getSection(),editTeacher.getAcademicYear()));
        EditGradeComboBox.setValue(editTeacher.getGrade());
        EditCourseComboBox.setValue(editTeacher.getCourseName());
//        EditSemesterComboBox.setValue(editTeacher.getSemester());
        }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        ObservableList<String> courses = FXCollections.observableArrayList(CourseDAO.getAllCourses());

        EditCourseComboBox.setItems(courses);

        if(EditGradeComboBox.getValue() != null) {
            ObservableList<String> coursesByGrade = FXCollections.observableArrayList(CourseDAO.getAllCoursesByGrade(EditGradeComboBox.getValue()));
            EditCourseComboBox.setItems(coursesByGrade);
        }

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        EditAcademicYearComboBox.setItems(calendars);

        EditGradeComboBox.setItems(grades);
        EditSectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));
        EditSemesterComboBox.setValue(Model.getInstance().getCurrentSemester());
    }


    public AssignedTeacher getNewAssignTeacherInfo() {
        String teacherId = selectedTeacherIdField.getText();
        String fullName = selectedTeacherNameField.getText();
        String semester=EditSemesterComboBox.getValue();
        String academicYear = EditAcademicYearComboBox.getValue();
        String course = EditCourseComboBox.getValue();
        String section = EditSectionComboBox.getValue();

        String grade = EditGradeComboBox.getValue();
        return new AssignedTeacher(teacherId,fullName,academicYear,grade,course,section,semester);
    }

    public void setNewAssignTeacherForEdit(Teacher teacher) {
        selectedTeacherIdField.setText(teacher.getTeacherId());
        selectedTeacherNameField.setText(teacher.getFullName());
    }
}

