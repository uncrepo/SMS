package org.project.sms.Controllers.Teacher;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.project.sms.Models.Model;
import org.project.sms.dao.ScheduleDAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;

public class TeacherScheduleController implements Initializable {
    public Button refreshBtn;
    public GridPane summaryGrid;
    @FXML private ComboBox<String> academicYearComboBox;
    @FXML private TableView<Map<String, String>> scheduleTableView;
    @FXML private TableColumn<Map<String, String>, String> colPeriod;
    @FXML private TableColumn<Map<String, String>, String> colMonday;
    @FXML private TableColumn<Map<String, String>, String> colTuesday;
    @FXML private TableColumn<Map<String, String>, String> colWednesday;
    @FXML private TableColumn<Map<String, String>, String> colThursday;
    @FXML private TableColumn<Map<String, String>, String> colFriday;
    @FXML private Label totalClassesLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private Label totalStudentsLabel;

    private final ScheduleDAO scheduleDAO;
    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] PERIODS = {"1", "2", "3", "4", "5", "6", "7"};
    private final LocalTime FIRST_PERIOD_START = LocalTime.of(8, 0);
    private final int PERIOD_DURATION = 45;
    private final int BREAK_DURATION = 5;

    String teacherId = Model.getInstance().getCurrentTeacher().getTeacherId();


    public TeacherScheduleController() {
        scheduleDAO = new ScheduleDAO();
        // Get teacher ID from logged-in user
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBox
        academicYearComboBox.setItems(FXCollections.observableArrayList("2022/23","2023/24", "2024/25"));
        academicYearComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadSchedule(newVal);
            }
        });

        // Initialize TableView columns
        initializeTableColumns();

        // Select current academic year
        academicYearComboBox.getSelectionModel().select("2022/23");
    }

    private void initializeTableColumns() {
        colPeriod.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("period")));
        colMonday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Monday")));
        colTuesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Tuesday")));
        colWednesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Wednesday")));
        colThursday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Thursday")));
        colFriday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Friday")));
    }

    @FXML
    private void handleRefresh() {
        String academicYear = academicYearComboBox.getValue();
        if (academicYear != null) {
            loadSchedule(academicYear);
        }
    }

    private void loadSchedule(String academicYear) {
        List<Map<String, String>> scheduleData = new ArrayList<>();
        Set<String> uniqueCourses = new HashSet<>();
        int totalClasses = 0;
        int totalStudents = 0;

        // Generate schedule data
        for (String periodNumber : PERIODS) {
            Map<String, String> rowData = new HashMap<>();
            LocalTime startTime = FIRST_PERIOD_START.plusMinutes((Integer.parseInt(periodNumber) - 1) * (PERIOD_DURATION + BREAK_DURATION));
            LocalTime endTime = startTime.plusMinutes(PERIOD_DURATION);

            rowData.put("period", String.format("Period %s\n%s-%s",
                    periodNumber,
                    startTime.toString(),
                    endTime.toString()));

            // Get schedule for each day
            for (String day : DAYS) {
                String classInfo = getClassInfo(teacherId, day, periodNumber, academicYear);
                if (classInfo != null) {
                    rowData.put(day, classInfo);
                    totalClasses++;

                    // Extract course name for unique count
                    String[] parts = classInfo.split("\n");
                    if (parts.length > 1) {
                        uniqueCourses.add(parts[1]); // Course name is second line
                    }

                    // Get student count for this class
                    totalStudents += getStudentCount(teacherId, day, periodNumber, academicYear);
                } else {
                    rowData.put(day, "");
                }
            }
            scheduleData.add(rowData);
        }

        // Update TableView
        scheduleTableView.setItems(FXCollections.observableArrayList(scheduleData));

        // Update summary
        totalClassesLabel.setText(String.valueOf(totalClasses));
        totalCoursesLabel.setText(String.valueOf(uniqueCourses.size()));
        totalStudentsLabel.setText(String.valueOf(totalStudents));
    }

    private String getClassInfo(String teacherId, String day, String periodNumber, String academicYear) {
        String query = "SELECT g.grade, s.section, c.course_name " +
                "FROM Schedules s " +
                "JOIN Grades g ON s.grade_id = g.grade_id " +
                "JOIN Courses c ON s.course_id = c.course_id " +
                "WHERE s.teacher_id = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherId);
            stmt.setString(2, day);
            stmt.setString(3, periodNumber);
            stmt.setString(4, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return String.format("%s %s\n%s",
                        rs.getString("grade"),
                        rs.getString("section"),
                        rs.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule information.", Alert.AlertType.ERROR);
        }
        return null;
    }

    private int getStudentCount(String teacherId, String day, String periodNumber, String academicYear) {
        String query = "SELECT COUNT(DISTINCT sc.student_id) as student_count " +
                "FROM Schedules s " +
                "JOIN Classes cl ON (s.grade_id = cl.grade_id AND s.section = cl.section AND s.academic_year = cl.academic_year) " +
                "JOIN student_class sc ON cl.class_id = sc.class_id " +
                "WHERE s.teacher_id = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, teacherId);
            stmt.setString(2, day);
            stmt.setString(3, periodNumber);
            stmt.setString(4, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
