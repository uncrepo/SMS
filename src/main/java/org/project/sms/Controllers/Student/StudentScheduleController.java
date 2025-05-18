package org.project.sms.Controllers.Student;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Model;
import org.project.sms.dao.ScheduleDAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;

public class StudentScheduleController implements Initializable {
    @FXML private ComboBox<String> academicYearComboBox;
    @FXML private Label gradeLabel;
    @FXML private Label sectionLabel;
    @FXML private Label classTeacherLabel;

    @FXML private TableView<Map<String, String>> scheduleTableView;
    @FXML private TableColumn<Map<String, String>, String> colPeriod;
    @FXML private TableColumn<Map<String, String>, String> colMonday;
    @FXML private TableColumn<Map<String, String>, String> colTuesday;
    @FXML private TableColumn<Map<String, String>, String> colWednesday;
    @FXML private TableColumn<Map<String, String>, String> colThursday;
    @FXML private TableColumn<Map<String, String>, String> colFriday;

    @FXML private TableView<CourseSummary> courseSummaryTableView;
    @FXML private TableColumn<CourseSummary, String> colCourseName;
    @FXML private TableColumn<CourseSummary, String> colTeacherName;
    @FXML private TableColumn<CourseSummary, String> colTeacherEmail;
    @FXML private TableColumn<CourseSummary, Integer> colClassesPerWeek;

    private final ScheduleDAO scheduleDAO;
    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] PERIODS = {"1", "2", "3", "4", "5", "6", "7"};
    private final LocalTime FIRST_PERIOD_START = LocalTime.of(8, 0);
    private final int PERIOD_DURATION = 45;
    private final int BREAK_DURATION = 5;
    private String studentId;
    private String currentGrade;
    private String currentSection;

    public static class CourseSummary {
        private final String courseName;
        private final String teacherName;
        private final String teacherEmail;
        private final int classesPerWeek;

        public CourseSummary(String courseName, String teacherName, String teacherEmail, int classesPerWeek) {
            this.courseName = courseName;
            this.teacherName = teacherName;
            this.teacherEmail = teacherEmail;
            this.classesPerWeek = classesPerWeek;
        }

        public String getCourseName() { return courseName; }
        public String getTeacherName() { return teacherName; }
        public String getTeacherEmail() { return teacherEmail; }
        public int getClassesPerWeek() { return classesPerWeek; }
    }

    public StudentScheduleController() {
        scheduleDAO = new ScheduleDAO();
        // Get student ID from logged-in user
        studentId = Model.getInstance().getCurrentStudent().getStudentId();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBox
        academicYearComboBox.setItems(FXCollections.observableArrayList("2023/24", "2024/25"));
        academicYearComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadStudentInfo(newVal);
                loadSchedule(newVal);
            }
        });

        // Initialize TableView columns
        initializeTableColumns();
        initializeCourseSummaryColumns();

        // Select current academic year
        academicYearComboBox.getSelectionModel().select("2024/25");
    }

    private void initializeTableColumns() {
        colPeriod.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("period")));
        colMonday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Monday")));
        colTuesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Tuesday")));
        colWednesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Wednesday")));
        colThursday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Thursday")));
        colFriday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Friday")));
    }

    private void initializeCourseSummaryColumns() {
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colTeacherEmail.setCellValueFactory(new PropertyValueFactory<>("teacherEmail"));
        colClassesPerWeek.setCellValueFactory(new PropertyValueFactory<>("classesPerWeek"));
    }

    @FXML
    private void handleRefresh() {
        String academicYear = academicYearComboBox.getValue();
        if (academicYear != null) {
            loadStudentInfo(academicYear);
            loadSchedule(academicYear);
        }
    }

    private void loadStudentInfo(String academicYear) {
        String query = "SELECT g.grade, c.section, t.first_name, t.last_name " +
                "FROM Students s " +
                "JOIN Classes c ON s.class_id = c.class_id " +
                "JOIN Grades g ON c.grade_id = g.grade_id " +
                "JOIN Teachers t ON c.class_teacher_id = t.teacher_id " +
                "WHERE s.student_id = ? AND c.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentGrade = rs.getString("grade");
                currentSection = rs.getString("section");
                String teacherName = rs.getString("first_name") + " " + rs.getString("last_name");

                gradeLabel.setText(currentGrade);
                sectionLabel.setText(currentSection);
                classTeacherLabel.setText(teacherName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load student information.", Alert.AlertType.ERROR);
        }
    }

    private void loadSchedule(String academicYear) {
        if (currentGrade == null || currentSection == null) return;

        List<Map<String, String>> scheduleData = new ArrayList<>();
        Map<String, CourseSummary> courseSummaries = new HashMap<>();

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
                String classInfo = getClassInfo(currentGrade, currentSection, day, periodNumber, academicYear);
                rowData.put(day, classInfo != null ? classInfo : "");

                // Update course summary if there's a class
                if (classInfo != null) {
                    updateCourseSummary(courseSummaries, currentGrade, currentSection, day, periodNumber, academicYear);
                }
            }
            scheduleData.add(rowData);
        }

        // Update TableViews
        scheduleTableView.setItems(FXCollections.observableArrayList(scheduleData));
        courseSummaryTableView.setItems(FXCollections.observableArrayList(courseSummaries.values()));
    }

    private String getClassInfo(String grade, String section, String day, String periodNumber, String academicYear) {
        String query = "SELECT c.course_name, t.first_name, t.last_name " +
                "FROM Schedules s " +
                "JOIN Courses c ON s.course_id = c.course_id " +
                "JOIN Teachers t ON s.teacher_id = t.teacher_id " +
                "WHERE s.grade_id = ? " +
                "AND s.section = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, grade);
            stmt.setString(2, section);
            stmt.setString(3, day);
            stmt.setString(4, periodNumber);
            stmt.setString(5, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String teacherName = rs.getString("first_name") + " " + rs.getString("last_name");
                return String.format("%s\n%s",
                        rs.getString("course_name"),
                        teacherName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateCourseSummary(Map<String, CourseSummary> summaries, String grade, String section,
                                     String day, String periodNumber, String academicYear) {
        String query = "SELECT c.course_id, c.course_name, t.first_name, t.last_name, t.email " +
                "FROM Schedules s " +
                "JOIN Courses c ON s.course_id = c.course_id " +
                "JOIN Teachers t ON s.teacher_id = t.teacher_id " +
                "WHERE s.grade_id = ? " +
                "AND s.section = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, grade);
            stmt.setString(2, section);
            stmt.setString(3, day);
            stmt.setString(4, periodNumber);
            stmt.setString(5, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String courseId = rs.getString("course_id");
                String courseName = rs.getString("course_name");
                String teacherName = rs.getString("first_name") + " " + rs.getString("last_name");
                String teacherEmail = rs.getString("email");

                if (summaries.containsKey(courseId)) {
                    // Update existing course summary
                    CourseSummary existing = summaries.get(courseId);
                    summaries.put(courseId, new CourseSummary(
                            existing.getCourseName(),
                            existing.getTeacherName(),
                            existing.getTeacherEmail(),
                            existing.getClassesPerWeek() + 1
                    ));
                } else {
                    // Create new course summary
                    summaries.put(courseId, new CourseSummary(
                            courseName,
                            teacherName,
                            teacherEmail,
                            1
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}