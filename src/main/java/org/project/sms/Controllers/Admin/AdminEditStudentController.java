package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditStudentController implements Initializable {
    public TableView<Student> studentTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colEmail;
    public TableColumn<Student, String> colGuardian;
    public TableColumn<Student, String> colPhone;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;

    public Button searchBtn;
    public Button toggleEnableBtn;
    public Button saveChangesBtn;
    public Button editBtn;
    public Button deleteStudentBtn;
    public Button resetBtn;

    public TextField selectedStudentNameField;
    public TextField selectedStudentIdField;
    public TextField searchStudentField;

    public TextField fullNameField;
    public TextField emailField;
    public TextField guardianField;
    public TextField phoneField;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedTeacher();
        BtnClicks();

        // Load the teacher data
        loadTeacherData();

        // Filter action on search
    }

    TeacherDAO teacherDAO = new TeacherDAO();


    private void loadTeacherData() {
        studentTableView.setItems(FXCollections.observableArrayList(StudentDAO.getAllStudents()));

    }


    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void initSelectedTeacher(){
        studentTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedStudentNameField.setText(newValue.getFullName());
                selectedStudentIdField.setText(newValue.getStudentId());
                fullNameField.setText(newValue.getFullName());
                emailField.setText(newValue.getEmail());
                guardianField.setText(newValue.getGuardian());
                phoneField.setText(newValue.getPhone());
            }
        });


    }



    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterStudents());
        deleteStudentBtn.setOnAction(e -> removeStudent());
        saveChangesBtn.setOnAction(e -> updateStudentInfo());
        resetBtn.setOnAction(e -> clearSearchFields());
    }

    private void updateStudentInfo() {
        String studentId = selectedStudentIdField.getText();

        String phone = phoneField.getText();
        String guardian = guardianField.getText();
        String email = emailField.getText();

        StudentDAO.updateStudent(studentId,email,phone, guardian);
        loadTeacherData();
        clearSelectedFields();
    }

    private void removeStudent() {
        String studentId = selectedStudentIdField.getText();
        StudentDAO.deleteStudent(studentId);
        loadTeacherData();
        clearSelectedFields();
        // show error message here
    }


    // Filter action on search
    private void filterStudents() {
        String searchText = searchStudentField.getText();

        // Perform filtering based on the input search text, department, and section
        studentTableView.setItems(StudentDAO.searchStudentsByName(searchText));
    }


    private void clearSelectedFields() {

        fullNameField.clear();
        emailField.clear();
        guardianField.clear();
        phoneField.clear();
        selectedStudentIdField.clear();
        selectedStudentNameField.clear();

    }

    private void clearSearchFields() {
        searchStudentField.clear();
    }

}

