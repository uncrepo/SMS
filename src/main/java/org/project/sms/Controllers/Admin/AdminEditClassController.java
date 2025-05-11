package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Class;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditClassController implements Initializable {
    public TableView<Class> classesTableView;
    public TableColumn<Class, String> colClassId;
    public TableColumn<Class, String> colAcademicYear;
    public TableColumn<Class, String> colGrade;
    public TableColumn<Class, String> colSection;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> academicYearComboBox;

    public Button searchBtn;
    public Button resetBtn;


    public Button toggleEnableBtn;
    public Button editBtn;


    public Button saveChangesBtn;
    public Button deleteBtn;

    public TextField selectedClassIdField;
    public ComboBox<String> EditAcademicYearComboBox;
    public ComboBox<String> EditGradeComboBox;
    public ComboBox<String> EditSectionComboBox;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedClass();
        initOptions();
        BtnClicks();

        // Load the teacher data
        loadClassesData();

    }



    private void loadClassesData() {
        classesTableView.setItems(FXCollections.observableArrayList(ClassDAO.getAllClassesDefault()));

    }


    private void initTableCols() {
        colClassId.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

    }

    private void initSelectedClass(){
        classesTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedClassIdField.setText(newValue.getClassId());
                EditSectionComboBox.setValue(newValue.getSection());
                EditGradeComboBox.setValue(newValue.getGrade());
                EditAcademicYearComboBox.setValue(newValue.getAcademicYear());
            }
        });


    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

        EditGradeComboBox.setItems(grades);
        EditAcademicYearComboBox.setItems(calendars);
        EditSectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));
    }



    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterClasses());
        deleteBtn.setOnAction(e -> removeClass());
        saveChangesBtn.setOnAction(e -> updateClassInfo());
        resetBtn.setOnAction(e -> clearSearchFields());
    }

    private void updateClassInfo() {
        String classId = selectedClassIdField.getText();

        String acedemicYear = EditAcademicYearComboBox.getValue();
        String grade = EditGradeComboBox.getValue();
        String section = EditSectionComboBox.getValue();

        ClassDAO.updateClass(classId,acedemicYear,grade, section);
        loadClassesData();
        clearSelectedFields();
    }

    private void removeClass() {
        String classId = selectedClassIdField.getText();
        ClassDAO.deleteClass(classId);
        loadClassesData();
        clearSelectedFields();
        // show error message here
    }


    // Filter action on search
    private void filterClasses() {
        String academicYear = EditAcademicYearComboBox.getValue();
        String grade = EditGradeComboBox.getValue();
        String section = EditSectionComboBox.getValue();

        // Perform filtering based on the input search text, department, and section
        classesTableView.setItems(FXCollections.observableArrayList(ClassDAO.searchClassesByYearGradeSection(academicYear,grade,section)));
    }


    private void clearSelectedFields() {
        EditAcademicYearComboBox.getSelectionModel().clearSelection();
        EditGradeComboBox.getSelectionModel().clearSelection();;
        EditSectionComboBox.getSelectionModel().clearSelection();;
        selectedClassIdField.clear();
    }

    private void clearSearchFields() {
        academicYearComboBox.getSelectionModel().clearSelection();
        gradeComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
    }
}
