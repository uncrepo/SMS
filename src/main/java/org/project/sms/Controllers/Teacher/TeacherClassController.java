package org.project.sms.Controllers.Teacher;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Result;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherClassController implements Initializable {
    public TableView teacherTableView;
    public TableColumn<Result,String> colTeacherId;
    public TableColumn <Result, String> colFullName;
    public TableColumn <Result, String> colAttendance;
    public TableColumn <Result, String> colIndivAsgn;
    public TableColumn <Result, String> colGrpAsgn;
    public TableColumn <Result, String> colMidExam;
    public TableColumn <Result, String> colFinalExam;
    public TableColumn <Result, String> colTotal;

    public Button BtnReset;
    public Button BtnEdit;
    public TextField studentName;
    public Button BtnSaveChanges;
    public Button BtnToggleEnable;

    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;
    public ComboBox gradeComboBox;
    public ComboBox academicYearComboBox;
    public ComboBox courseComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumns();
    }

    private void initTableColumns() {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colIndivAsgn.setCellValueFactory(new PropertyValueFactory<>("indivAsgn"));
        colGrpAsgn.setCellValueFactory(new PropertyValueFactory<>("grpAsgn"));
        colMidExam.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colFinalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colAttendance.setOnEditCommit(event -> {
            event.getRowValue().setAttendance(event.getNewValue());
        });
        colIndivAsgn.setOnEditCommit(event -> {
            event.getRowValue().setIndivAsgn(event.getNewValue());
        });
        colGrpAsgn.setOnEditCommit(event -> {
            event.getRowValue().setGrpAsgn(event.getNewValue());
        });
        colFinalExam.setOnEditCommit(event -> {
            event.getRowValue().setFinalExam(event.getNewValue());
        });
        colMidExam.setOnEditCommit(event -> {
            event.getRowValue().setMidExam(event.getNewValue());
        });
        colTotal.setOnEditCommit(event -> {
            event.getRowValue().setTotal(event.getNewValue());
        });
    }


}
