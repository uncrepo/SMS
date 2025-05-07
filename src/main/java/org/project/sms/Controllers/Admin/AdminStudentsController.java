package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminStudentsController implements Initializable {
    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;
    public TableView teacherTableView;
    public TableColumn colTeacherId;
    public TableColumn colFullName;
    public TableColumn colEmail;
    public TableColumn colPhone;
    public TableColumn colGuardian;
    public TableColumn colAcademicYear;
    public TableColumn colGrade;
    public TableColumn colStudentYearResult;
    public TextField searchStudentField;
    public ComboBox GradeComboBox;
    public ComboBox AcademicYearComboBox;
    public Button searchBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
