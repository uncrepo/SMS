package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.TeacherDAO;
import org.project.sms.options.DepartmentOptions;
import org.project.sms.options.SectionOptions;

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

    public ComboBox<String>EditGradeComboBox;
    public TextField teacherNameField;
    public ComboBox <String>EditCourseComboBox;
    public ComboBox<String> EditAcademicYearComboBox;
    public ComboBox <String>academicYearComboBox;

    public ComboBox<String> sortByComboBox;
    public ComboBox <String>filterComboBox;
    public ComboBox <String>gradeComboBox;
    public ComboBox <String>courseComboBox;

    public TextField selectedTeacherField;
    public Button searchBtn;
    public Button searchByFilterBtn;
    public Button assignTeacherBtn;
    public ComboBox classAdvisorBtn;
    public ComboBox EditCourseComboBox1;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();

        // Load the teacher data
        loadTeacherData();

        // Filter action on search
    }

    TeacherDAO teacherDAO = new TeacherDAO();


    private void loadTeacherData() {
        teacherTableView.setItems(FXCollections.observableArrayList(teacherDAO.getAllTeachersDefault()));
    }


    private void initTableCols() {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
    }


}
