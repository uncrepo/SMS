package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminAssignTeacherController implements Initializable {
    public TableView<Teacher> teacherTableView;
    public TableColumn<Teacher, String> colTeacherId;
    public TableColumn <Teacher, String>colFullName;
    public TableColumn<Teacher, String> colAcademicYear;
    public TableColumn<Teacher, String> colCourse;
    public TableColumn <Teacher, String>colGrade;
    public TableColumn <Teacher, String>colSection;

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
    public Button updateAssignTeacherBtn;
    public Button resetBtn;

    public TableView<Teacher> teacherTableViewNotAssigned;
    public TableColumn<Teacher, String> colTeacherIdNotAssigned;
    public TableColumn<Teacher, String> colFullNameNotAssigned;
    public TableColumn<Teacher, String> colPhoneNotAssigned;
    public TableColumn<Teacher, String> colEmailNotAssigned;
//    public ComboBox classAdvisorComboBox;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedTeacher();
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

        EditCourseComboBox.setItems(courses);

        if(EditGradeComboBox.getValue() != null) {
            ObservableList<String> coursesByGrade = FXCollections.observableArrayList(CourseDAO.getAllCoursesByGrade(EditGradeComboBox.getValue()));
            EditCourseComboBox.setItems(coursesByGrade);
        }
        EditGradeComboBox.setItems(grades);
        EditAcademicYearComboBox.setItems(calendars);
        EditSectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));
        EditSemesterComboBox.setItems(FXCollections.observableArrayList("1","2"));
    }

    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterTeachers());
        assignTeacherBtn.setOnAction(e -> assignTeacher());
        unassignTeacherBtn.setOnAction(e -> unassignTeacher());
        updateAssignTeacherBtn.setOnAction(e -> updateAssignedTeacher());
        resetBtn.setOnAction(e -> clearSearchFields());
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
        clearSelectedFields();
    }

    private void unassignTeacher() {
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

}
