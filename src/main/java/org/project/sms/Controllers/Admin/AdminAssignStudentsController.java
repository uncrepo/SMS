package org.project.sms.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Class;
import org.project.sms.Models.NotAssignedStudent;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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


    public TableView<NotAssignedStudent> studentsTableViewNotAssigned;
    public TableColumn<Student, String> colStudentIdNotAssigned;
    public TableColumn<Student, String> colFullNameNotAssigned;
    public TableColumn<Student, String> colAverageNotAssigned;
    public TableColumn<Student, String> colPreviousGrade;
    public TableColumn<Student, String> colPromotionNotAssigned;
    public TableColumn<Student, String> colComment;
    public TableColumn<Student, String> colLastYear;
    public Button previousNotAssignedBtn;
    public Button nextNotAssignedBtn;
    public Button previousAssignedBtn;
    public Button nextAssignedBtn;

    private int currentPage = 0;
    private final int ROWS_PER_PAGE = 7;

//    public ComboBox classAdvisorComboBox;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedStudent();
        initOptions();
        BtnClicks();

        // Load the students data


        setupComboBoxListeners();
        setupPaginationButtons();

        loadFilteredPage();
        loadFilteredNotAssignedPage();

        // Filter action on search
    }

//    private void loadStudentData() {
//        studentsTableView.setItems(FXCollections.observableArrayList(StudentDAO.getAssignedStudents()));
//        studentsTableViewNotAssigned.setItems(FXCollections.observableArrayList(StudentDAO.getStudentsNotAssigned()));
//    }


    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        colFullNameNotAssigned.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colStudentIdNotAssigned.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colPreviousGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colAverageNotAssigned.setCellValueFactory(new PropertyValueFactory<>("average"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colLastYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));

    }

    private void setupComboBoxListeners() {
        ChangeListener<String> comboListener = (obs, oldVal, newVal) -> {
            currentPage = 0;
            loadFilteredPage();
        };

        academicYearComboBox.valueProperty().addListener(comboListener);
        gradeComboBox.valueProperty().addListener(comboListener);
        sectionComboBox.valueProperty().addListener(comboListener);
    }


    private void setupPaginationButtons() {
        previousAssignedBtn.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadFilteredPage();
            }
        });

        nextAssignedBtn.setOnAction(event -> {
            currentPage++;
            loadFilteredPage();
        });

        previousNotAssignedBtn.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadFilteredNotAssignedPage();
            }
        });

        nextNotAssignedBtn.setOnAction(event -> {
            currentPage++;
            loadFilteredNotAssignedPage();
        });
    }

    private void loadFilteredPage() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();

        int offset = currentPage * ROWS_PER_PAGE;

        List<Student> results = FilterDAO.getStudentsByFilter(
                academicYear, grade, section, ROWS_PER_PAGE, offset
        );

        studentsTableView.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousAssignedBtn.setDisable(currentPage == 0);
        nextAssignedBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }

    private void loadFilteredNotAssignedPage() {

        int offset = currentPage * ROWS_PER_PAGE;

        List<NotAssignedStudent> results = FilterDAO.getNotAssignedStudentsByFilter(
                ROWS_PER_PAGE, offset
        );

        studentsTableViewNotAssigned.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousAssignedBtn.setDisable(currentPage == 0);
        nextAssignedBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }


    private void initSelectedStudent(){
        studentsTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                clearSelectedFields();
                selectedStudentNameField.setText(newValue.getFullName());
                selectedStudentIdField.setText(newValue.getStudentId());
                EditAcademicYearComboBox.setValue(newValue.getAcademicYear());
                EditGradeComboBox.setValue(newValue.getGrade());
                EditSectionComboBox.setValue(newValue.getSection());
                prevClassIdField.setText(ClassDAO.getClassID(newValue.getGrade(),newValue.getSection(),newValue.getAcademicYear()));
            }
        });

        studentsTableViewNotAssigned.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                clearSelectedFields();
                selectedStudentNameField.setText(newValue.getFullName());
                selectedStudentIdField.setText(newValue.getStudentId());
                prevClassIdField.setText(null);
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

        if (assignedStudentId == null) {
            showAlert("No Selection", "Please select a student or multiple students to update assign.", Alert.AlertType.WARNING);
            return;
        }
        String academicYear = EditAcademicYearComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String grade = EditGradeComboBox.getValue();

        String prevClassId = prevClassIdField.getText();
        String studentId = StudentDAO.getStudentIdByAssignedId(assignedStudentId);
        String newClassId = ClassDAO.getClassID(grade,section,academicYear);

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Update Assign Student");
        confirmDialog.setHeaderText("Assign Student");
        confirmDialog.setContentText("Are you sure you want to update " + selectedStudentNameField.getText() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (StudentDAO.checkAssignedStudentExist(assignedStudentId)) {
                StudentDAO.removeStudentResultsFromClass(studentId, prevClassId);
                StudentDAO.updateAssignedStudent(assignedStudentId, newClassId);
                StudentDAO.assignResultsToStudent(studentId, newClassId);
                showAlert("Success", "Student updated assign successfully!", Alert.AlertType.INFORMATION);
                currentPage=0;
                loadFilteredPage();
                clearSelectedFields();
            }
        } else {
            // show error message here
            showAlert("Failed", "Failed to update assigning " + selectedStudentNameField.getText()+"  !", Alert.AlertType.INFORMATION);

        }
    }

    private void unassignStudent() {
        String assignId = selectedStudentIdField.getText();
        if (assignId == null) {
            showAlert("No Selection", "Please select a student to to unassign.", Alert.AlertType.WARNING);
            return;
        }

        String classId = prevClassIdField.getText();
        String studentId = StudentDAO.getStudentIdByAssignedId(assignId);

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Unassign Student");
        confirmDialog.setHeaderText("Unassign Student");
        confirmDialog.setContentText("Are you sure you want to delete " + selectedStudentNameField.getText() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        if (StudentDAO.checkAssignedStudentExist(assignId)) {
            StudentDAO.removeStudentResultsFromClass(studentId, classId);
            StudentDAO.unassignStudent(assignId);
            StudentDAO.setNotAssigned(studentId);
            showAlert("Success", "Student unassigned successfully!", Alert.AlertType.INFORMATION);
            clearSelectedFields();
            currentPage=0;
            loadFilteredPage();
        }
        } else {
            // show error message here
            System.out.println("There's not Assigned Student with this Id.");
        }

    }

    private void assignStudents() {
        String academicYear = EditAcademicYearComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String grade = EditGradeComboBox.getValue();

        String studentId = selectedStudentIdField.getText();
        String classId = ClassDAO.getClassID(grade,section,academicYear);

        if (studentId == null) {
            showAlert("No Selection", "Please select a student or multiple students to assign.", Alert.AlertType.WARNING);
            return;
        }
        if (classId == null) {
            showAlert("Failed", "Grade doesn't exist, make sure grade for this year was created", Alert.AlertType.WARNING);
            return;
        }

        if (!StudentDAO.checkAssignedStudentExist(studentId,classId)) {
            // Perform assigning a teacher to a class.
            StudentDAO.assignStudentToClass(studentId,classId);
            StudentDAO.assignResultsToStudent(studentId,classId);
            StudentDAO.setAlreadyAssigned(studentId);
            showAlert("Success", "Student assigned successfully!", Alert.AlertType.INFORMATION);
            clearSelectedFields();
            currentPage=0;
            loadFilteredPage();
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
        EditGradeComboBox.setValue(null);
        EditAcademicYearComboBox.setValue(null);
        EditSectionComboBox.setValue(null);

        gradeComboBox.setValue("default");
        sectionComboBox.setValue("default");
        academicYearComboBox.setValue("default");

        selectedStudentIdField.clear();
        selectedStudentNameField.clear();
        studentsTableViewNotAssigned.getSelectionModel().clearSelection();
        studentsTableView.getSelectionModel().clearSelection();
    }

    private void clearSearchFields() {
        gradeComboBox.setValue("default");
        sectionComboBox.setValue("default");
        academicYearComboBox.setValue("default");
    }

//    private void setupCommonListener() {
//        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadStudentsFromOptions();
//        gradeComboBox.valueProperty().addListener(commonListener);
//        sectionComboBox.valueProperty().addListener(commonListener);
//        academicYearComboBox.valueProperty().addListener(commonListener);
//    }
//
//    private void loadStudentsFromOptions() {
//        String grade = gradeComboBox.getValue();
//        String section = sectionComboBox.getValue();
//        String academicYear = academicYearComboBox.getValue();
//
//        studentsTableView.setItems(FXCollections.observableArrayList(FilterDAO.getStudentsByFilter(academicYear,grade,section,)));
//
//    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
