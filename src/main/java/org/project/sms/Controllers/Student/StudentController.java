package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.project.sms.Models.Model;
import org.project.sms.options.AccountType;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public BorderPane student_parent;
    public Label greetText;
    public Label roleText;
    public Label loggedInText;

    private final String name = Model.getInstance().getCurrentStudent().getFullName();
    private final AccountType role = Model.getInstance().getCurrentUserRole().getValue();
    private final String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuOptions();
        initValues();
    }

    private void initValues() {
        greetText.setText("Hello "+name+", Welcome back");
        roleText.setText("CURRENT ROLE : " + role);
        loggedInText.setText("last Logged in : " + time);
    }

    private void MenuOptions() {
        Model.getInstance().getStudentSelectedMenu().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case CURRENT_SEMESTER -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentCurrentSemester());
                case ASSIGNMENTS -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentAssignmentView());
                case RESULTS -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentResultsView());
                case PROFILE -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentProfileView());
            }
        });

    }
}

