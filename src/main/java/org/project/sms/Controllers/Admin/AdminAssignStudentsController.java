package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Class;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminAssignStudentsController implements Initializable {
    public TableView<Student> studentsTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn <Student, String>colFullName;
    public TableColumn<Student, String> colAcademicYear;
    public TableColumn <Student, String>colGrade;
    public TableColumn <Student, String>colSection;

    public ComboBox<String> EditSectionComboBox;
    public ComboBox<String>EditGradeComboBox;
    public ComboBox<String> EditAcademicYearComboBox;


    public ComboBox <String>academicYearComboBox;
    public ComboBox <String>gradeComboBox;
    public ComboBox <String>sectionComboBox;

    public TextField prevClassIdField;

    public TextField selectedStudentNameField;
    public TextField selectedStudentIdField;
    public TextField searchStudentField;

    public Button searchBtn;
    public Button resetBtn;

    public Button assignStudentBtn;
    public Button unassignStudentBtn;
    public Button updateAssignStudentBtn;


    public TableView<Student> studentsTableViewNotAssigned;
    public TableColumn<Student, String> colStudentIdNotAssigned;
    public TableColumn<Student, String> colFullNameNotAssigned;
    public TableColumn<Student, String> colAverageNotAssigned;
    public TableColumn<Student, String> colPromotionNotAssigned;

//    public ComboBox classAdvisorComboBox;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedStudent();
        initOptions();
        BtnClicks();

        // Load the teacher data
        loadStudentData();

        // Filter action on search
    }


    private void loadStudentData() {
        studentsTableView.setItems(FXCollections.observableArrayList(StudentDAO.getAssignedStudents()));
    }


    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        colFullNameNotAssigned.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colStudentIdNotAssigned.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colPromotionNotAssigned.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        colAverageNotAssigned.setCellValueFactory(new PropertyValueFactory<>("average"));
    }

    private void initSelectedStudent(){
        studentsTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedStudentNameField.setText(newValue.getFullName());
                selectedStudentIdField.setText(newValue.getStudentId());
                EditAcademicYearComboBox.setValue(newValue.getAcademicYear());
                EditGradeComboBox.setValue(newValue.getGrade());
                EditSectionComboBox.setValue(newValue.getSection());
                prevClassIdField.setText(ClassDAO.getClassID(newValue.getGrade(),newValue.getSection(),newValue.getAcademicYear()));
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
        searchBtn.setOnAction(e -> filterStudents());
        assignStudentBtn.setOnAction(e -> assignStudents());
        updateAssignStudentBtn.setOnAction(e -> updateAssignedStudent());
        unassignStudentBtn.setOnAction(e -> unassignStudent());
        resetBtn.setOnAction(e -> clearSearchFields());
    }


    private void updateAssignedStudent() {
        String assignedStudentId = selectedStudentIdField.getText();

        String academicYear = EditAcademicYearComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String grade = EditGradeComboBox.getValue();

        String prevClassId = prevClassIdField.getText();
        String studentId = StudentDAO.getStudentIdByAssignedId(assignedStudentId);
        String newClassId = ClassDAO.getClassID(grade,section,academicYear);

        if (StudentDAO.checkAssignedStudentExist(assignedStudentId)) {
            StudentDAO.removeStudentResultsFromClass(studentId, prevClassId);
            StudentDAO.updateAssignedStudent(assignedStudentId, newClassId);
            StudentDAO.assignResultsToStudent(studentId,newClassId);
            loadStudentData();
            clearSelectedFields();
        } else {
            // show error message here
            System.out.println("There's not Assigned Student with this Id.");
        }
    }

    private void unassignStudent() {
        String assignId = selectedStudentIdField.getText();
        String classId = selectedStudentIdField.getText();
        String studentId = StudentDAO.getStudentIdByAssignedId(assignId);

        if (StudentDAO.checkAssignedStudentExist(assignId)) {
            StudentDAO.removeStudentResultsFromClass(studentId, classId);
            StudentDAO.unassignStudent(assignId);
            loadStudentData();
            clearSelectedFields();
        } else {
            // show error message here
            System.out.println("There's not Assigned Student with this Id.");
        }

    }

    private void assignStudents() {
        String academicYear = academicYearComboBox.getValue();
        String section = sectionComboBox.getValue();
        String grade = gradeComboBox.getValue();

        String studentId = selectedStudentIdField.getText();
        String classId = ClassDAO.getClassID(grade,section,academicYear);


        if (!StudentDAO.checkAssignedStudentExist(studentId,classId)) {
            // Perform assigning a teacher to a class.
            StudentDAO.assignStudentToClass(studentId,classId);
            StudentDAO.assignResultsToStudent(studentId,classId);
            loadStudentData();
            clearSelectedFields();
        } else {
            // show error message here
            System.out.println("The Student Already assigned to a class.");
        }
    }

    // Filter action on search
    private void filterStudents() {
        String searchText = searchStudentField.getText();
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();

        // Perform filtering based on the input search text, department, and section
        studentsTableView.setItems(StudentDAO.searchAssignedStudentsByName(searchText, grade, section, academicYear));
        clearSearchFields();
    }


    private void clearSelectedFields() {
        EditGradeComboBox.getSelectionModel().clearSelection();
        EditAcademicYearComboBox.getSelectionModel().clearSelection();
        EditSectionComboBox.getSelectionModel().clearSelection();

        gradeComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();

        selectedStudentIdField.clear();
        selectedStudentNameField.clear();

    }

    private void clearSearchFields() {
        gradeComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
    }
}
