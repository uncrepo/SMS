package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Models.AssignedTeacher;
import org.project.sms.Models.NotAssignedStudent;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminAssignTeacherController implements Initializable {
    public TableView<AssignedTeacher> teacherTableView;
    public TableColumn<AssignedTeacher, String> colTeacherId;
    public TableColumn <AssignedTeacher, String>colFullName;
    public TableColumn<AssignedTeacher, String> colAcademicYear;
    public TableColumn<AssignedTeacher, String> colCourse;
    public TableColumn <AssignedTeacher, String>colGrade;
    public TableColumn <AssignedTeacher, String>colSection;

    public TextField selectedTeacherNameField;
    public TextField selectedTeacherIdField;
    public ComboBox<String> EditSectionComboBox;
    public ComboBox<String>EditGradeComboBox;
    public ComboBox <String>EditCourseComboBox;
    public ComboBox<String> EditAcademicYearComboBox;
    public ComboBox<String> EditSemesterComboBox;

    public ComboBox<String> classRepresentativeComboBox;

    public ComboBox <String>academicYearComboBox;
    public ComboBox <String>gradeComboBox;
    public ComboBox <String>courseComboBox;
    public ComboBox <String>sectionComboBox;

    public TextField prevClassIdField;
    public TextField searchTeacherField;


    public Button searchBtn;
    public Button assignTeacherBtn;
    public Button unassignTeacherBtn;
    public Button resetBtn;

    public TableView<Teacher> teacherTableViewNotAssigned;
    public TableColumn<Teacher, String> colTeacherIdNotAssigned;
    public TableColumn<Teacher, String> colFullNameNotAssigned;
    public TableColumn<Teacher, String> colPhoneNotAssigned;
    public TableColumn<Teacher, String> colEmailNotAssigned;
    public Button previousAssignedTeachersBtn;
    public Button nextAssignedTeachersBtn;
    public Button previousNotAssignedTeachersBtn;
    public Button nextNotAssignedTeachersBtn;
    public Button editAssignTeacherBtn;
    public TableColumn colSemester;

    private int currentPage = 0;
    private int currentPageNotAssigned = 0;

    private final int ROWS_PER_PAGE = 6;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();
        BtnClicks();

        // Load the teacher data
        loadTeacherData();

        // Filter action on search
    }



    private void loadTeacherData() {
        teacherTableView.setItems(FXCollections.observableArrayList(TeacherDAO.getAssignedTeachers()));
        teacherTableViewNotAssigned.setItems(TeacherDAO.getAllTeachersForAssign());

    }


    private void initTableCols() {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        colTeacherIdNotAssigned.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colFullNameNotAssigned.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhoneNotAssigned.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmailNotAssigned.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void initSelectedTeacher(){
        teacherTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedTeacherNameField.setText(newValue.getFullName());
                selectedTeacherIdField.setText(newValue.getTeacherId());
                EditAcademicYearComboBox.setValue(newValue.getAcademicYear());
                EditCourseComboBox.setValue(newValue.getCourseName());
                EditGradeComboBox.setValue(newValue.getGrade());
                EditSectionComboBox.setValue(newValue.getSection());
                prevClassIdField.setText(ClassDAO.getClassID(newValue.getGrade(),newValue.getSection(),newValue.getAcademicYear()));
            }
        });

        teacherTableViewNotAssigned.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedTeacherNameField.setText(newValue.getFullName());
                selectedTeacherIdField.setText(newValue.getTeacherId());

            }
        });
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        ObservableList<String> courses = FXCollections.observableArrayList(CourseDAO.getAllCourses());
        courseComboBox.setItems(courses);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));
        if(gradeComboBox.getValue() != null) {
            ObservableList<String> coursesByGrade = FXCollections.observableArrayList(CourseDAO.getAllCoursesByGrade(gradeComboBox.getValue()));
            courseComboBox.setItems(coursesByGrade);
        }

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

    }

    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterTeachers());
        assignTeacherBtn.setOnAction(e -> newAssignTeacherDialog());
        unassignTeacherBtn.setOnAction(e -> unassignTeacher());
        editAssignTeacherBtn.setOnAction(e -> editAssignedTeacherDialog());
        resetBtn.setOnAction(e -> clearSearchFields());
    }

    private void editAssignedTeacherDialog() {
        try {
            AssignedTeacher editTeacher = teacherTableView.getSelectionModel().getSelectedItem();
            if (editTeacher == null) {
                showAlert("No Selection", "Please select a teacher to edit.", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_assign_teacher_dialog.fxml"));
            AnchorPane anchorPane = loader.load();
            AdminAssignTeacherDialogController controller = loader.getController();

            controller.setAssignedTeacherForEdit(editTeacher);

            Dialog<AssignedTeacher> dialog = new Dialog<>();
            dialog.setTitle("Edit Assigned Teacher");
            dialog.setHeaderText("Edit Teacher Information");

            dialog.getDialogPane().setContent(anchorPane);

            // Set the button types
            ButtonType saveButtonType = new ButtonType("Update Teacher", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    AssignedTeacher updated = controller.getEditAssignedTeacherValues();
                    editTeacher.setAcademicYear(updated.getAcademicYear());
                    editTeacher.setGrade(updated.getGrade());
                    editTeacher.setSection(updated.getSection());
                    editTeacher.setSemester(updated.getSemester());
                    return editTeacher;
                }
                return null;
            });

            Optional<AssignedTeacher> result = dialog.showAndWait();
            result.ifPresent(updateTeacher -> {

                // Update the teacher in the database
                String assignedTeacherId = updateTeacher.getTeacherId();
                String teacherId = TeacherDAO.getTeacherIdByAssignedId(assignedTeacherId);
                String academicYear = updateTeacher.getAcademicYear();
                String section = updateTeacher.getSection();
                String grade = updateTeacher.getGrade();
                String course = updateTeacher.getCourseName();
                String semester = updateTeacher.getSemester();

                String prevClassId = updateTeacher.getPrevClassId();
                String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
                String newClassId = ClassDAO.getClassID(grade,section,academicYear);

                AssignmentDAO.unassignTeacherFromAssignment(prevClassId,gradeCourseId,teacherId,semester);
                TeacherDAO.removeTeacherFromResults(prevClassId, gradeCourseId, teacherId,semester);
                TeacherDAO.updateAssignedTeacher(assignedTeacherId,gradeCourseId,newClassId);
                TeacherDAO.assignTeacherToResults(newClassId,gradeCourseId,teacherId,semester);
                AssignmentDAO.assignTeacherToAssignment(newClassId,gradeCourseId,teacherId,semester);
                showAlert("Success", "Teacher updated assign to ... updated successfully!", Alert.AlertType.INFORMATION);
                loadTeacherData(); // Refresh the table
            });

        } catch (IOException e) {
            showAlert("Failed", "Teacher information isn't updated!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    private void newAssignTeacherDialog() {
        try {
            Teacher teacher = teacherTableViewNotAssigned.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_assign_teacher_dialog.fxml"));
            AnchorPane anchorPane = loader.load();

            Dialog<AssignedTeacher> dialog = new Dialog<>();
            dialog.setTitle("Assign Teacher");
            dialog.setHeaderText("Add Grade Information");

            AdminAssignTeacherDialogController controller = loader.getController();
            dialog.getDialogPane().setContent(anchorPane);

            controller.setNewAssignTeacherForEdit(teacher);


            // Set the button types
            ButtonType saveButtonType = new ButtonType("Add Teacher", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    AssignedTeacher updated = controller.getNewAssignTeacherInfo();
                    return new AssignedTeacher(
                            updated.getTeacherId(),
                            updated.getFullName(),
                            updated.getAcademicYear(),
                            updated.getGrade(),
                            updated.getCourseName(),
                            updated.getSection(),
                            updated.getSemester()
                    );
                }
                return null;
            });

            Optional<AssignedTeacher> result = dialog.showAndWait();
            result.ifPresent(newAssign -> {
                // Update the teacher in the database
                String academicYear = newAssign.getAcademicYear();
                String section = newAssign.getSection();
                String grade = newAssign.getGrade();
                String course = newAssign.getCourseName();
                String semester = newAssign.getSemester();

                String teacherId = newAssign.getTeacherId();
                String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
                String classId = ClassDAO.getClassID(grade,section,academicYear);


                if (!TeacherDAO.checkAssignedTeacherExist(gradeCourseId,classId)) {
                    // Perform assigning a teacher to a class.
                    TeacherDAO.assignTeacherToClass(teacherId,gradeCourseId,classId);
                    TeacherDAO.assignTeacherToResults(classId,gradeCourseId,teacherId,semester);
                    AssignmentDAO.assignTeacherToAssignment(classId,gradeCourseId,teacherId,semester);
                    showAlert("Success", " Teacher was assigned successfully!", Alert.AlertType.INFORMATION);
                    loadTeacherData();
                } else {
                    // show error message here
                    showAlert("Failed", " Teacher was already assigned for this Course!", Alert.AlertType.INFORMATION);
                }
            });
        } catch (IOException e) {
            showAlert("Failed", " Database error!", Alert.AlertType.INFORMATION);

        }
    }


    private void unassignTeacher() {
        AssignedTeacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        if (selectedTeacher == null) {
            showAlert("No Selection", "Please select a teacher to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Teacher");
        confirmDialog.setContentText("Are you sure you want to delete " + selectedTeacher.getFullName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the teacher from the database
            String assignId = selectedTeacher.getTeacherId();
            String teacherId = TeacherDAO.getTeacherIdByAssignedId(assignId);
            String grade = selectedTeacher.getGrade();
            String course = selectedTeacher.getCourseName();
            String academicYear = selectedTeacher.getAcademicYear();
            String section = selectedTeacher.getSection();
            String semester = selectedTeacher.getSemester();

            String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
            String prevClassId = ClassDAO.getClassID(grade,section,academicYear);

            if (TeacherDAO.checkAssignedTeacherExist(assignId)) {
                AssignmentDAO.unassignTeacherFromAssignment(prevClassId,gradeCourseId,teacherId,semester);
                TeacherDAO.removeTeacherFromResults(prevClassId,gradeCourseId,teacherId,semester);
                TeacherDAO.unassignTeacher(assignId);
                showAlert("Success", "Teacher unassigned successfully!", Alert.AlertType.INFORMATION);
                loadTeacherData();
            } else {
                // show error message here
                System.out.println("There's not Assigned Teacher");
            }
        }
    }


    private void updateAssignedTeacher() {
        String assignedTeacherId = selectedTeacherIdField.getText();
        String teacherId = TeacherDAO.getTeacherIdByAssignedId(assignedTeacherId);

        String academicYear = EditAcademicYearComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String grade = EditGradeComboBox.getValue();
        String course = EditCourseComboBox.getValue();
        String semester = EditSemesterComboBox.getValue();


        String prevClassId = prevClassIdField.getText();
        String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
        String newClassId = ClassDAO.getClassID(grade,section,academicYear);

        TeacherDAO.removeTeacherFromResults(prevClassId, gradeCourseId, teacherId,semester);
        TeacherDAO.updateAssignedTeacher(assignedTeacherId,gradeCourseId,newClassId);
        TeacherDAO.assignTeacherToResults(newClassId,gradeCourseId,teacherId,semester);
        loadTeacherData();
    }

    private void unassignTeacherOUTDATED() {
        String assignId = selectedTeacherIdField.getText();
        String teacherId = TeacherDAO.getTeacherIdByAssignedId(assignId);
        String grade = EditGradeComboBox.getValue();
        String course = EditCourseComboBox.getValue();
        String semester = EditSemesterComboBox.getValue();

        String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
        String prevClassId = prevClassIdField.getText();

        if (TeacherDAO.checkAssignedTeacherExist(assignId)) {
            TeacherDAO.removeTeacherFromResults(prevClassId,gradeCourseId,teacherId,semester);
            TeacherDAO.unassignTeacher(assignId);
            loadTeacherData();
            clearSelectedFields();
        } else {
            // show error message here
            System.out.println("There's not Assigned Teacher");
        }

    }

    private void assignTeacher() {
        String academicYear = EditAcademicYearComboBox.getValue();
        String section = EditSectionComboBox.getValue();
        String grade = EditGradeComboBox.getValue();
        String course = EditCourseComboBox.getValue();
        String semester = EditSemesterComboBox.getValue();

        String teacherId = selectedTeacherIdField.getText();
        String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);
        String classId = ClassDAO.getClassID(grade,section,academicYear);


            if (!TeacherDAO.checkAssignedTeacherExist(gradeCourseId,classId)) {
                // Perform assigning a teacher to a class.
                TeacherDAO.assignTeacherToClass(teacherId,gradeCourseId,classId);
                TeacherDAO.assignTeacherToResults(classId,gradeCourseId,teacherId,semester);
                loadTeacherData();
                clearSelectedFields();
            } else {
                // show error message here
                System.out.println("an Assigned Teacher Already exists! ");
            }
    }

    // Filter action on search
    private void filterTeachers() {
    String searchText = searchTeacherField.getText();
    String academicYear = academicYearComboBox.getValue();
    String grade = gradeComboBox.getValue();
    String course = courseComboBox.getValue();
    String section = sectionComboBox.getValue();

    // Perform filtering based on the input search text, department, and section
    teacherTableView.setItems(TeacherDAO.searchAssignedTeachersByName(searchText, grade, section, academicYear,course));
    teacherTableViewNotAssigned.setItems(TeacherDAO.getAllTeachersForAssign());

}

    private void setupPaginationButtons() {
        previousAssignedTeachersBtn.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadFilteredPage();
            }
        });

        nextAssignedTeachersBtn.setOnAction(event -> {
            currentPage++;
            loadFilteredPage();
        });

        previousNotAssignedTeachersBtn.setOnAction(event -> {
            if (currentPageNotAssigned > 0) {
                currentPageNotAssigned--;
                loadFilteredNotAssignedPage();
            }
        });

        nextNotAssignedTeachersBtn.setOnAction(event -> {
            currentPageNotAssigned++;
            loadFilteredNotAssignedPage();
        });
    }


    private void clearSelectedFields() {
        EditGradeComboBox.getSelectionModel().clearSelection();
        EditAcademicYearComboBox.getSelectionModel().clearSelection();
        EditSectionComboBox.getSelectionModel().clearSelection();
        EditCourseComboBox.getSelectionModel().clearSelection();

        gradeComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();

        selectedTeacherIdField.clear();
        selectedTeacherNameField.clear();

    }

    private void clearSearchFields() {

        gradeComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
    }

    private void loadFilteredPage() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();


        int offset = currentPage * ROWS_PER_PAGE;

        List<AssignedTeacher> results = FilterDAO.getAssignedTeachersByFilter(
                academicYear, grade, section, ROWS_PER_PAGE, offset
        );

        teacherTableView.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousAssignedTeachersBtn.setDisable(currentPage == 0);
        nextAssignedTeachersBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }

    private void loadFilteredNotAssignedPage() {

        int offset = currentPageNotAssigned * ROWS_PER_PAGE;

        List<Teacher> results = FilterDAO.getNotAssignedTeachersByFilter(
                ROWS_PER_PAGE, offset
        );

        teacherTableViewNotAssigned.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousNotAssignedTeachersBtn.setDisable(currentPageNotAssigned == 0);
        nextNotAssignedTeachersBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
