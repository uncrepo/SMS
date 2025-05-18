package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import org.project.sms.Models.AddStudent;
import org.project.sms.Models.Class;
import org.project.sms.Models.Model;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminAddClassController implements Initializable {


    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> assignClassroomComboBox;
    public ComboBox<String> assignAdvisorComboBox;
    public Label heading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }


    private void addClass() {
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();

        ClassDAO.addClass(grade,section,academicYear);
    }

    public Class getNewClassInfo() {
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();
        return new Class(grade,section,academicYear);
    }


    public Class getEditClassValues() {
        String grade = gradeComboBox.getValue();
        String section=sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();

        return new Class(grade,section,academicYear);
    }


    public void setClassForEdit(Class editClass) {
        gradeComboBox.setValue(editClass.getGrade());
        academicYearComboBox.setValue(editClass.getAcademicYear());
        sectionComboBox.setValue(editClass.getSection());
    }

    public void setHeading(String text) {
        heading.setText(text);
    }
}
