package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.project.sms.Models.Model;
import org.project.sms.dao.CalendarDAO;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminProfileController implements Initializable {
    public TextField adminFullNameField;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> semesterComboBox;
    public ComboBox<String> startCalendar;
    public ComboBox<String> endCalender;
    public Button updateBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAdmin();
        initOptions();
    }


    private void initOptions() {
        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

        semesterComboBox.setItems(FXCollections.observableArrayList("1","2"));
    }


    private void initAdmin(){
        adminFullNameField.setText(Model.getInstance().getCurrentAdmin().getFullName());
        academicYearComboBox.setValue(Model.getInstance().getCurrentAcademicYear());
        semesterComboBox.setValue(Model.getInstance().getCurrentAcademicYear());
        startCalendar.setItems(FXCollections.observableArrayList("YES","NO"));
        endCalender.setItems(FXCollections.observableArrayList("YES","NO"));
        }


}
