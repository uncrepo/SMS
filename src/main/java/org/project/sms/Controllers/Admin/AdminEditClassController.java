package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditClassController implements Initializable {
    public TableView teacherTableView;
    public TableColumn colTeacherId;
    public TableColumn colAcademicYear;
    public TableColumn colGrade;
    public TableColumn colCourse;

    public ComboBox sortByComboBox;
    public ComboBox filterComboBox;
    public ComboBox academicYearComboBox;
    public ComboBox gradeComboBox;
    public ComboBox courseComboBox;

    public TextField selectedClassField;
    public Button deleteBtn;
    public Button editBtn;
    public Button searchByFilterBtn;
    public Button saveChangesBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
