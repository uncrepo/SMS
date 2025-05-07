package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import org.project.sms.Models.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentCellController implements Initializable {

    private final Student student;
    public StudentCellController(Student student) {
        this.student = student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
