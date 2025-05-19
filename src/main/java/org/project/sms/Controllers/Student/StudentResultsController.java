package org.project.sms.Controllers.Student;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Model;
import org.project.sms.Models.Result;
import org.project.sms.Models.Student;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentResultsController implements Initializable {
    public ComboBox<String> academicYearComboBox;
    public Label averageLabel1;
    public Label averageLabel2;
    public Label section;
    public Label grade;
    public Label academic;

    public TableView<Result> studentTableView;
    public TableColumn<Result, String> colCourseFirstSemester;
    public TableColumn<Result, String> colAttendance;
    public TableColumn<Result, String> colIndivAsgn;
    public TableColumn<Result, String> colGrpAsgn;
    public TableColumn<Result, String> colMidExam;
    public TableColumn<Result, String> colFinalExam;
    public TableColumn<Result, String> colTotal;
    
    public TableView<Result> studentTableView2;
    public TableColumn<Result, String> colCourseSecondSemester;
    public TableColumn<Result, String> colAttendance2;
    public TableColumn<Result, String> colIndivAsgn2;
    public TableColumn<Result, String> colGrpAsgn2;
    public TableColumn<Result, String> colMidExam2;
    public TableColumn<Result, String> colFinalExam2;
    public TableColumn<Result, String> colTotal2;

    private final String studentId = Model.getInstance().getCurrentStudent().getStudentId();
    public Label totalAverageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
        initTableCols();
        setupCommonListener();
    }

    private void initOptions() {
        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }

    private void initTableCols() {
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colIndivAsgn.setCellValueFactory(new PropertyValueFactory<>("IndivAsgn"));
        colGrpAsgn.setCellValueFactory(new PropertyValueFactory<>("GrpAsgn"));
        colMidExam.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colFinalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        colCourseFirstSemester.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colAttendance2.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colIndivAsgn2.setCellValueFactory(new PropertyValueFactory<>("IndivAsgn"));
        colGrpAsgn2.setCellValueFactory(new PropertyValueFactory<>("GrpAsgn"));
        colMidExam2.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colFinalExam2.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        colCourseSecondSemester.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colTotal2.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadStudentsResult();
        academicYearComboBox.valueProperty().addListener(commonListener);
    }

    private void loadStudentsResult() {
        String academicYear = academicYearComboBox.getValue();

        studentTableView.setItems(FXCollections.observableArrayList(ResultDAO.getStudentFirstSemesterResults(academicYear, studentId)));
        studentTableView2.setItems(FXCollections.observableArrayList(ResultDAO.getStudentSecondSemesterResults(academicYear,studentId)));

        calculateSemesterAverages();
    }

    public void calculateSemesterAverages() {
        ObservableList<Result> results = studentTableView.getItems();
        ObservableList<Result> results2 = studentTableView2.getItems();

        String academicYear = academicYearComboBox.getValue();

        academic.setText(academicYear);
        section.setText(OptionsDAO.getStudentSection(studentId,academicYear));
        grade.setText(OptionsDAO.getStudentGrade(studentId,academicYear));


        double firstSemesterTotal = 0;
        int firstCount = 0;

        double secondSemesterTotal = 0;
        int secondCount = 0;

        for (Result result : results) {
            firstSemesterTotal += Float.parseFloat(result.getTotal());
            firstCount++;
        }
        for (Result result2 : results2) {
            secondSemesterTotal += Float.parseFloat(result2.getTotal());
            secondCount++;
        }

        double firstAvg = firstCount > 0 ? firstSemesterTotal / firstCount : 0;
        double secondAvg = secondCount > 0 ? secondSemesterTotal / secondCount : 0;
        double finalAvg = (firstAvg + secondAvg) / 2;

        averageLabel1.setText(String.valueOf(firstAvg));
        averageLabel2.setText(String.valueOf(secondAvg));
        totalAverageLabel.setText(String.format("%.2f", finalAvg));
    }


    }



