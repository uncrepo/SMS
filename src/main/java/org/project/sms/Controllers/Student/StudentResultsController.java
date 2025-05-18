package org.project.sms.Controllers.Student;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.project.sms.Models.Model;
import org.project.sms.Models.Result;
import org.project.sms.Models.Student;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentResultsController implements Initializable {
    public ComboBox<String> academicYearComboBox;
    public TextField averageField;
    public TextField sectionField;
    public TextField gradeField;
    public TextField academicField;

    public TableView<Result> studentTableView;
    public TableColumn<Result, String> colFullName;
    public TableColumn<Result, String> colAttendance;
    public TableColumn<Result, String> colIndivAsgn;
    public TableColumn<Result, String> colGrpAsgn;
    public TableColumn<Result, String> colMidExam;
    public TableColumn<Result, String> colFinalExam;
    public TableColumn<Result, String> colTotal;
    
    public TableView<Result> studentTableView2;
    public TableColumn<Result, String> colFullName2;
    public TableColumn<Result, String> colAttendance2;
    public TableColumn<Result, String> colIndivAsgn2;
    public TableColumn<Result, String> colGrpAsgn2;
    public TableColumn<Result, String> colMidExam2;
    public TableColumn<Result, String> colFinalExam2;
    public TableColumn<Result, String> colTotal2;

    private final String studentId = Model.getInstance().getCurrentStudent().getStudentId();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
        setupCommonListener();
    }

    private void initOptions() {
        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }

    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadStudentsResult();
        academicYearComboBox.valueProperty().addListener(commonListener);
    }

    private void loadStudentsResult() {
        String academicYear = academicYearComboBox.getValue();

        studentTableView.setItems(FXCollections.observableArrayList(ResultDAO.getStudentFirstSemesterResults(academicYear, studentId)));
        studentTableView2.setItems(FXCollections.observableArrayList(ResultDAO.getStudentSecondSemesterResults(academicYear,studentId)));

    }


}
