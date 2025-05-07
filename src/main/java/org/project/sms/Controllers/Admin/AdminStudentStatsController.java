package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminStudentStatsController implements Initializable {
    public TableView teacherTableView;
    public TableColumn colFullName;
    public TableColumn colAttendance;
    public TableColumn colIndivAsgn;
    public TableColumn colGrpAsgn;
    public TableColumn colMidExam;
    public TableColumn colFinalExam;
    public TableColumn colTotal;

    public TextField myAcademicField;
    public TextField myGradeField;
    public TextField mySectionFIeld;


    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;
    public ComboBox academicYearComboBox;


    public TableView teacherTableView2;
    public TableColumn colFullName2;
    public TableColumn colAttendance2;
    public TableColumn colIndivAsgn2;
    public TableColumn colGrpAsgn2;
    public TableColumn colMidExam2;
    public TableColumn colFinalExam2;
    public TableColumn colTotal2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
