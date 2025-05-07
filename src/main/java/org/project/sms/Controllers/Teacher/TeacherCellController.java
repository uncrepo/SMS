package org.project.sms.Controllers.Teacher;

import javafx.fxml.Initializable;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherCellController implements Initializable {
    private final Teacher teacher;
    public TeacherCellController(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
