package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.project.sms.Models.Teacher;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminEditTeacherDialogController implements Initializable {
    public TextField fullNameField;
    public ToggleGroup genderGroup;
    public TextField emailField;
    public TextField phoneNumberField;
    public TextField statusField;
    public TextField guardianNumberField;
    public TextField teacherUsernameField;
    public Button generateUsernameBtn;
    public Button usernameCopy;
    public TextField passwordField;
    public Button generatePasswordBtn;
    public Button passwordCopy;
    public Button resetBtn;
    public ImageView uploadedImageView;
    public Button uploadImage;
    public ImageView uploadedNationalIDView;
    public Button uploadDocument;
    public RadioButton maleRadio;
    public RadioButton femaleRadio;
    public ComboBox<String> statusComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusComboBox.setItems(FXCollections.observableArrayList("ENABLED","DISABLED","PENDING"));
        
    }

    public Teacher getEditTeacherValues() {
        String fullName = fullNameField.getText();
        String email=emailField.getText();
        String guardian = guardianNumberField.getText();
        String phone = phoneNumberField.getText();
        String status = statusComboBox.getValue();
        RadioButton gender =  (RadioButton) genderGroup.getSelectedToggle();
        String selectedGender = gender.getText();

        return new Teacher(fullName,email,phone,guardian,selectedGender, status);
    }


    public void setTeacherForEdit(Teacher editTeacher) {
        fullNameField.setText(editTeacher.getFullName());
        emailField.setText(editTeacher.getEmail());
        guardianNumberField.setText(editTeacher.getGuardian());
        phoneNumberField.setText(editTeacher.getPhone());
        statusComboBox.setValue(editTeacher.getStatus());
        if (editTeacher.getGender() == null)
            genderGroup.selectToggle(null);
        if (Objects.equals(editTeacher.getGender(), "male")) {
            genderGroup.selectToggle(maleRadio);
        }
        else if (Objects.equals(editTeacher.getGender(), "female")) {
            genderGroup.selectToggle(femaleRadio);
        }
    }
}
