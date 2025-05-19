package org.project.sms.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Models.AddStudent;
import org.project.sms.Models.Class;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    public Button resetBtn;


    public Button editBtn;


    public Button deleteBtn;

    public TextField selectedClassIdField;
    public Button addGradeBtn;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedClass();
        initOptions();
        BtnClicks();
        setupComboBoxListeners();

        // Load the teacher data
        loadClassesData();

    }



    private void loadClassesData() {
        classesTableView.setItems(FXCollections.observableArrayList(ClassDAO.getAllClassesDefault()));

    }

    private void addClassDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_class.fxml"));
            AnchorPane anchorPane = loader.load();

            Dialog<Class> dialog = new Dialog<>();
            dialog.setTitle("Add Grade");
            dialog.setHeaderText("Add Grade Information");

            AdminAddClassController controller = loader.getController();
            controller.setHeading("Add new Grade");

            dialog.getDialogPane().setContent(anchorPane);

            // Set the button types
            ButtonType saveButtonType = new ButtonType("Add Grade", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    Class updated = controller.getNewClassInfo();
                    return new Class (
                            updated.getGrade(),
                            updated.getSection(),
                            updated.getAcademicYear()
                    );
                }
                return null;
            });

            Optional<Class> result = dialog.showAndWait();
            result.ifPresent(newClass -> {
                // Update the teacher in the database
                ClassDAO.addClass(newClass.getGrade(), newClass.getSection(), newClass.getAcademicYear());
                showAlert("Success", "A new Grade was created successfully!", Alert.AlertType.INFORMATION);
                loadClassesData(); // Refresh the table
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            }
        });


    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        // set current later
        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

    }



    private void BtnClicks() {
        deleteBtn.setOnAction(e -> removeClass());
        resetBtn.setOnAction(e -> clearSearchFields());
        addGradeBtn.setOnAction(e -> addClassDialog());
        editBtn.setOnAction(e -> editClassDialog());
    }


    private void removeClass() {
        Class selectedClass = classesTableView.getSelectionModel().getSelectedItem();
        if (selectedClass == null) {
            showAlert("No Selection", "Please select a grade to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Grade");
        confirmDialog.setContentText("Are you sure you want to delete " + selectedClass.getGrade() + selectedClass.getSection() + " for the " + selectedClass.getAcademicYear() +" year ?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the teacher from the database
            ClassDAO.deleteClass(selectedClass.getClassId());
            showAlert("Success", "Grade deleted successfully!", Alert.AlertType.INFORMATION);
            loadClassesData(); // Refresh the table
        }
    }


    private void editClassDialog() {
        try {
            Class editClass = classesTableView.getSelectionModel().getSelectedItem();
            if (editClass == null) {
                showAlert("No Selection", "Please select a Class to edit.", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_class.fxml"));
            AnchorPane anchorPane = loader.load();
            AdminAddClassController controller = loader.getController();

            controller.setClassForEdit(editClass);
            controller.setHeading("Update Existing Grade Info");
            Dialog<Class> dialog = new Dialog<>();
            dialog.setTitle("Edit Class");
            dialog.setHeaderText("Edit Class Information");

            dialog.getDialogPane().setContent(anchorPane);


            // Set the button types
            ButtonType saveButtonType = new ButtonType("Update Grade", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);


            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    Class updated = controller.getEditClassValues();
                    editClass.setAcademicYear(updated.getFullName());
                    editClass.setSection(updated.getEmail());
                    editClass.setGrade(updated.getPhone());
                    return editClass;
                }
                return null;
            });

            Optional<Class> result = dialog.showAndWait();
            result.ifPresent(updated -> {
                // Update the teacher in the database
                ClassDAO.updateClass(editClass.getClassId(),updated.getGrade(),updated.getSection(),updated.getAcademicYear());
                showAlert("Success", "Grade information updated successfully!", Alert.AlertType.INFORMATION);
                loadClassesData(); // Refresh the table
            });

        } catch (IOException e) {
            showAlert("Failed", "Couldn't update grade information!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    // Filter action on search
    private void filterClasses() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();

        // Perform filtering based on the input search text, department, and section
        classesTableView.setItems(FXCollections.observableArrayList(ClassDAO.searchClassesByYearGradeSection(academicYear,grade,section)));
    }

    private void setupComboBoxListeners() {
        ChangeListener<String> comboListener = (obs, oldVal, newVal) -> {
            filterClasses();
        };
    }


    private void clearSearchFields() {
        academicYearComboBox.getSelectionModel().clearSelection();
        gradeComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        selectedClassIdField.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
