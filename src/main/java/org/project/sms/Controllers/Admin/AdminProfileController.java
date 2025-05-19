package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.project.sms.Models.Model;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.SettingDAO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AdminProfileController implements Initializable {
    public TextField adminFullNameField;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<Integer> semesterComboBox;
    public Label calendarStartLabel;
    public Label calendarEndLabel;
    public Button updateBtn;
    public Label maintenanceLabel;
    public Label defaultPasswordLabel;
    public Label semesterLabel;
    public Label academicYearLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshSettings();
    }

    private void refreshSettings() {
        adminFullNameField.setText(Model.getInstance().getCurrentAdmin().getFullName());
        academicYearLabel.setText(SettingDAO.getCurrentAcademicYear());
        semesterLabel.setText(String.valueOf(SettingDAO.getCurrentSemester()));
        calendarStartLabel.setText(SettingDAO.getCalendarStart().toString());
        calendarEndLabel.setText(SettingDAO.getCalendarEnd().toString());
        maintenanceLabel.setText(SettingDAO.isMaintenanceMode() ? "Enabled" : "Disabled");
        defaultPasswordLabel.setText(SettingDAO.getDefaultPassword());
    }

    // --- Individual Edit Handlers ---
    public void editAcademicYear() {
        showChoiceDialog("Edit Academic Year", CalendarDAO.getAllCalendar(), academicYearLabel.getText(), newVal -> {
            SettingDAO.setCurrentAcademicYear(newVal);
            refreshSettings();
        });
    }

    public void editSemester() {
        showChoiceDialog("Edit Semester", List.of("1", "2"), semesterLabel.getText(), newVal -> {
            SettingDAO.setCurrentSemester(Integer.parseInt(newVal));
            refreshSettings();
        });
    }

    public void editCalendarStart() {
        showDatePickerDialog("Edit Calendar Start", SettingDAO.getCalendarStart(), newVal -> {
            SettingDAO.setCalendarStart(newVal);
            refreshSettings();
        });
    }

    public void editCalendarEnd() {
        showDatePickerDialog("Edit Calendar End", SettingDAO.getCalendarEnd(), newVal -> {
            SettingDAO.setCalendarEnd(newVal);
            refreshSettings();
        });
    }

    public void editMaintenanceMode() {
        boolean current = SettingDAO.isMaintenanceMode();
        boolean confirmed = showConfirmation("Toggle Maintenance Mode?", "Current: " + (current ? "Enabled" : "Disabled"));
        if (confirmed) {
            SettingDAO.setMaintenanceMode(!current);
            refreshSettings();
        }
    }

    public void editDefaultPassword() {
        showTextInputDialog("Edit Default Password", defaultPasswordLabel.getText(), newVal -> {
            SettingDAO.setDefaultPassword(newVal);
            refreshSettings();
        });
    }

    private void showTextInputDialog(String title, String defaultValue, Consumer<String> onConfirm) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new value:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            if (showConfirmation("Are you sure?", "Save new value: " + input)) {
                onConfirm.accept(input);
            }
        });
    }

    private void showChoiceDialog(String title, List<String> options, String defaultVal, Consumer<String> onConfirm) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultVal, options);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText("Select value:");

        dialog.showAndWait().ifPresent(choice -> {
            if (showConfirmation("Are you sure?", "Save new value: " + choice)) {
                onConfirm.accept(choice);
            }
        });
    }

    private void showDatePickerDialog(String title, LocalDate currentValue, Consumer<LocalDate> onConfirm) {
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle(title);
        DatePicker datePicker = new DatePicker(currentValue);
        dialog.getDialogPane().setContent(datePicker);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> btn == saveButton ? datePicker.getValue() : null);
        dialog.showAndWait().ifPresent(newDate -> {
            if (showConfirmation("Are you sure?", "Save new date: " + newDate)) {
                onConfirm.accept(newDate);
            }
        });
    }

    private boolean showConfirmation(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


}
