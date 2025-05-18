package org.project.sms.Controllers.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Model;
import org.project.sms.Models.Result;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentSemesterController implements Initializable {
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> semesterComboBox;
    public ComboBox<String> sectionComboBox;

    public TableView<Result> studentTableView;
    public TableColumn<Result,String> colSubject;
    public TableColumn<Result,String> colAttendance;
    public TableColumn<Result,String> colIndivAsgn;
    public TableColumn<Result,String> colGrpAsgn;
    public TableColumn<Result,String> colMidExam;
    public TableColumn<Result,String> colFinalExam;
    public TableColumn<Result,String> colTotal;
    public TextField academicYearField;
    public TextField gradeField;
    public TextField sectionField;

    private final String studentId = Model.getInstance().getCurrentStudent().getStudentId();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();

        // Load the teacher data
        loadStudentCurrentSemesterData();

    }


    private void loadStudentCurrentSemesterData() {
        String semester = semesterComboBox.getValue();
        String classId = ClassDAO.getClassID(gradeComboBox.getValue(),sectionComboBox.getValue(),academicYearComboBox.getValue());
        String studentId = Model.getInstance().getCurrentStudent().getStudentId();
        studentTableView.setItems(FXCollections.observableArrayList(ResultDAO.getStudentCurrentSemester(studentId,semester,classId)));
    }



    private void initTableCols() {
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colIndivAsgn.setCellValueFactory(new PropertyValueFactory<>("IndivAsgn"));
        colGrpAsgn.setCellValueFactory(new PropertyValueFactory<>("GrpAsgn"));
        colMidExam.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colFinalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }


    private void initOptions() {
        List<String> grade = OptionsDAO.getStudentGrade(studentId);
        gradeComboBox.setItems(FXCollections.observableArrayList(grade));

        List<String> section = OptionsDAO.getStudentSection(studentId);
        sectionComboBox.setItems(FXCollections.observableArrayList(section));

        semesterComboBox.setValue(Model.getInstance().getCurrentSemester());

        academicYearComboBox.setValue(Model.getInstance().getCurrentAcademicYear());

    }

}
