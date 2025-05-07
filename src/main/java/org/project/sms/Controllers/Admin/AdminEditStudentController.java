package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditStudentController implements Initializable {
    public TableView teacherTableView;
    public TableColumn colTeacherId;
    public TableColumn colFullName;
    public TableColumn colAcademicYear;
    public TableColumn colGrade;
    public TableColumn colCourse;

    public ComboBox academicYearComboBox;
    public ComboBox gradeComboBox;
    public ComboBox courseComboBox;
    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;

    public TextField searchTeacherField;
    public TextField selectedStudentField;

    public Button searchBtn;
    public Button searchByFilterBtn;
    public Button saveChangesBtn;
    public Button deleteBtn;
    public Button editBtn;
    public Button toggleEnableBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
