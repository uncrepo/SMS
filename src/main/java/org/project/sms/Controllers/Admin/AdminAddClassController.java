package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminAddClassController implements Initializable {
    public Button addClassBtn;
    public Button resetBtn;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> assignClassroomComboBox;
    public ComboBox<String> assignAdvisorComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BtnClicks();
        initOptions();
    }

    private void BtnClicks() {
        addClassBtn.setOnAction(e -> addClass());
        resetBtn.setOnAction(e -> clearFields());
    }

    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }

    private void clearFields() {
        gradeComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
    }

    private void addClass() {
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();

        ClassDAO.addClass(grade,section,academicYear);
        clearFields();
    }
}
