package org.project.sms.Controllers.Teacher;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Model;
import org.project.sms.Models.Result;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherClassController implements Initializable {
    public TableView<Result> studentsTableView;
    public TableColumn<Result,String> colStudentId;
    public TableColumn <Result, String> colFullName;
    public TableColumn <Result, String> colAttendance;
    public TableColumn <Result, String> colIndivAsgn;
    public TableColumn <Result, String> colGrpAsgn;
    public TableColumn <Result, String> colMidExam;
    public TableColumn <Result, String> colFinalExam;
    public TableColumn <Result, String> colTotal;

    public Button BtnReset;
    public Button BtnEdit;
    public Button ButSubmitApproval;
    public Button BtnSaveChanges;

    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> semesterComboBox;
    public ComboBox<String> courseComboBox;

    public TextField selectedStudentNameField;
    public TextField selectedStudentIdField;
    public TextField selectedResultIdField;
    public TextField attendanceField;
    public TextField indivAsgnField;
    public TextField groupAsgnField;
    public TextField midExamField;
    public TextField finalExamField;

    private final String teacherId = Model.getInstance().getCurrentTeacher().getTeacherId();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumns();
        initOptions();
        initSelectedStudent();
        BtnClicks();

        setupCommonListener();
    }

    private void initTableColumns() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colIndivAsgn.setCellValueFactory(new PropertyValueFactory<>("indivAsgn"));
        colGrpAsgn.setCellValueFactory(new PropertyValueFactory<>("grpAsgn"));
        colMidExam.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colFinalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

//        colAttendance.setOnEditCommit(event -> {
//            event.getRowValue().setAttendance(event.getNewValue());
//        });
//        colIndivAsgn.setOnEditCommit(event -> {
//            event.getRowValue().setIndivAsgn(event.getNewValue());
//        });
//        colGrpAsgn.setOnEditCommit(event -> {
//            event.getRowValue().setGrpAsgn(event.getNewValue());
//        });
//        colFinalExam.setOnEditCommit(event -> {
//            event.getRowValue().setFinalExam(event.getNewValue());
//        });
//        colMidExam.setOnEditCommit(event -> {
//            event.getRowValue().setMidExam(event.getNewValue());
//        });
//        colTotal.setOnEditCommit(event -> {
//            event.getRowValue().setTotal(event.getNewValue());
//        });




    }

    private void initSelectedStudent(){
        studentsTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedStudentNameField.setText(newValue.getFullName());
                selectedResultIdField.setText(newValue.getStudentId());
                attendanceField.setText(newValue.getAttendance());
                midExamField.setText(newValue.getmidExam());
                finalExamField.setText(newValue.getFinalExam());
                indivAsgnField.setText(newValue.getIndivAsgn());
                groupAsgnField.setText(newValue.getGrpAsgn());
            }
        });
    }

    private void loadStudentsResult() {
        String grade = gradeComboBox.getValue();
        String course = courseComboBox.getValue();

        String classId = ClassDAO.getClassID(grade,sectionComboBox.getValue(),academicYearComboBox.getValue());
        String semester = semesterComboBox.getValue();
        String gradeCourseId = GradeDAO.getGradeCourseID(grade,course);

        studentsTableView.setItems(FXCollections.observableArrayList(ResultDAO.getCurrentActiveClass(classId,semester,gradeCourseId,teacherId)));
    }


    private void BtnClicks() {
        BtnSaveChanges.setOnAction(e -> saveChanges());
        BtnReset.setOnAction(e -> clearSelectedFields());

    }

    private void saveChanges() {
        String resultId = selectedResultIdField.getText();

        String attendance = attendanceField.getText();
        String indivAsgn = indivAsgnField.getText();
        String groupAsgn = groupAsgnField.getText();
        String midExam = midExamField.getText();
        String finalExam = finalExamField.getText();

        ResultDAO.updateResult(resultId,attendance,indivAsgn,groupAsgn,midExam,finalExam);
        loadStudentsResult();
        clearSelectedFields();
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(OptionsDAO.getAllGrades(teacherId));
        gradeComboBox.setItems(grades);

        ObservableList<String> courses = FXCollections.observableArrayList(OptionsDAO.getAllCourses(teacherId));
        courseComboBox.setItems(courses);

        ObservableList<String> sections = FXCollections.observableArrayList(OptionsDAO.getAllSections(teacherId));
        sectionComboBox.setItems(sections);

        ObservableList<String> calendars = FXCollections.observableArrayList(OptionsDAO.getAllAcademicYears(teacherId));
        academicYearComboBox.setItems(calendars);

        ObservableList<String> semesters = FXCollections.observableArrayList(OptionsDAO.getAllSemesters(teacherId));
        semesterComboBox.setItems(semesters);

    }

    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs,oldVal, newVal) -> loadStudentsResult();
        gradeComboBox.valueProperty().addListener(commonListener);
        courseComboBox.valueProperty().addListener(commonListener);
        sectionComboBox.valueProperty().addListener(commonListener);
        academicYearComboBox.valueProperty().addListener(commonListener);
        semesterComboBox.valueProperty().addListener(commonListener);
    }

    // Filter action on search
//    private void filterTeachers() {
//        String searchText = searchTeacherField.getText();
//
//        // Perform filtering based on the input search text, department, and section
//        studentsTableView.setItems(TeacherDAO.searchTeachersByName(searchText));
//    }


    private void clearSelectedFields() {

        selectedStudentNameField.clear();
        selectedResultIdField.clear();
        midExamField.clear();
        finalExamField.clear();
        attendanceField.clear();
        indivAsgnField.clear();
        groupAsgnField.clear();

    }


}
