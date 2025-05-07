package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditTeacherController implements Initializable {
    public TableColumn colTeacherId;
    public TableColumn colFullName;
    public TableView teacherTableView;
    public TableColumn colAcademicYear;
    public TableColumn colGrade;
    public TableColumn colCourse;

    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;
    public ComboBox gradeComboBox;
    public ComboBox courseComboBox;
    public ComboBox academicYearComboBox;

    public TextField selectedTeacherField;
    public TextField searchTeacherField;

    public Button toggleEnableBtn;
    public Button searchBtn;
    public Button saveChangesBtn;
    public Button editBtn;
    public Button deleteBtn;
    public Button searchByFilterBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
