package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.project.sms.Models.AddStudent;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminEditStudentDialogController implements Initializable {
    public TextField fullNameField;
    public ToggleGroup genderGroup;
    public TextField emailField;
    public TextField phoneNumberField;
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

    public AddStudent getEditStudentValues() {
        String fullName = fullNameField.getText();
        String email=emailField.getText();
        String guardian = guardianNumberField.getText();
        String phone = phoneNumberField.getText();
        String status = statusComboBox.getValue();
        RadioButton gender =  (RadioButton) genderGroup.getSelectedToggle();
        String selectedGender = gender.getText();

        return new AddStudent(fullName,email,phone,guardian,selectedGender, status);
    }


    public void setStudentForEdit(Student editStudent) {
        fullNameField.setText(editStudent.getFullName());
        emailField.setText(editStudent.getEmail());
        guardianNumberField.setText(editStudent.getGuardian());
        phoneNumberField.setText(editStudent.getPhone());
        statusComboBox.setValue(editStudent.getStatus());
        if (editStudent.getGender() == null)
            genderGroup.selectToggle(null);
        if (Objects.equals(editStudent.getGender(), "male")) {
            genderGroup.selectToggle(maleRadio);
        }
        else if (Objects.equals(editStudent.getGender(), "female")) {
            genderGroup.selectToggle(femaleRadio);
        }
    }
}
