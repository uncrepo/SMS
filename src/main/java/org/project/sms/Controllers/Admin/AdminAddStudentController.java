package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.project.sms.dao.GradeDAO;
import org.project.sms.dao.StudentDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminAddStudentController implements Initializable {
    public TextField fullNameField;
    public ComboBox<String> gradeComboBox;
    public TextField emailField;
    public TextField guardianNumberField;
    public TextField phoneNumberField;
    public TextField StudentUsernameField;
    public TextField passwordField;

    public Button addStudentBtn;
    public Button resetBtn;
    public Button cancelBtn;

    public Button generatePasswordBtn;
    public Button generateUsernameBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        BtnClicks();
    }

    private void BtnClicks() {
        addStudentBtn.setOnAction(e -> addStudent());
        resetBtn.setOnAction(e -> clearFields());
        generatePasswordBtn.setOnAction(e -> generatePassword());
        generateUsernameBtn.setOnAction(e -> generateUsername());
    }

    private void generateUsername() {
    }

    private void generatePassword() {
    }

    private void clearFields() {
        fullNameField.clear();
        emailField.clear();
        guardianNumberField.clear();
        phoneNumberField.clear();
        StudentUsernameField.clear();
        passwordField.clear();
        gradeComboBox.getSelectionModel().clearSelection();
    }

    private void addStudent() {
        String fullName = fullNameField.getText();
        String email=emailField.getText();
        String guardian = guardianNumberField.getText();
        String phone = phoneNumberField.getText();
        String username = StudentUsernameField.getText();
        String password = passwordField.getText();

        StudentDAO.addStudent(fullName,username,email,password,guardian,phone);
        clearFields();
    }


}
