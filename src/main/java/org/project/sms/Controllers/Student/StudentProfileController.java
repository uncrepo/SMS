package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.project.sms.Models.Model;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentProfileController implements Initializable {
    public TextField studentNameField;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> semesterComboBox;
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> sectionComboBox;
    public TextField emailField;
    public TextField guardianNumberField;
    public TextField phoneNumberField;

    public RadioButton maleRadio;
    public RadioButton femaleRadio;
    public ToggleGroup genderGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStudent();
    }

    private void initStudent(){
        studentNameField.setText(Model.getInstance().getCurrentStudent().getFullName());
        academicYearComboBox.setValue(Model.getInstance().getCurrentAcademicYear());
        gradeComboBox.setValue("Grade 1");
        sectionComboBox.setValue("A");
        semesterComboBox.setValue("1");
        emailField.setText(Model.getInstance().getCurrentStudent().getEmail());
        phoneNumberField.setText(Model.getInstance().getCurrentStudent().getPhone());
        guardianNumberField.setText(Model.getInstance().getCurrentStudent().getGuardian());
        if (Model.getInstance().getCurrentStudent().getGender() == null)
            genderGroup.selectToggle(null);
        if (Objects.equals(Model.getInstance().getCurrentStudent().getGender(), "male")) {
            genderGroup.selectToggle(maleRadio);
        }
        else if (Objects.equals(Model.getInstance().getCurrentStudent().getGender(), "female")) {
            genderGroup.selectToggle(femaleRadio);
        }
    }
}
