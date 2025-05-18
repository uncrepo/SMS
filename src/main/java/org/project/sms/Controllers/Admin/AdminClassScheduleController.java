package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.project.sms.Models.Model;
import org.project.sms.Models.Schedule;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.ScheduleDAO;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class AdminClassScheduleController implements Initializable {
    @FXML private ComboBox<String> gradeComboBox;
    @FXML private ComboBox<String> sectionComboBox;
    @FXML private ComboBox<String> academicYearComboBox;
    @FXML private Button generateBtn;
    @FXML private Button clearBtn;
    @FXML private TableView<Map<String, String>> scheduleTableView;
    @FXML private TableColumn<Map<String, String>, String> colPeriod;
    @FXML private TableColumn<Map<String, String>, String> colMonday;
    @FXML private TableColumn<Map<String, String>, String> colTuesday;
    @FXML private TableColumn<Map<String, String>, String> colWednesday;
    @FXML private TableColumn<Map<String, String>, String> colThursday;
    @FXML private TableColumn<Map<String, String>, String> colFriday;
    @FXML private VBox conflictBox;
    @FXML private ListView<String> conflictListView;

    private final ScheduleDAO scheduleDAO;
    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] PERIODS = {"1", "2", "3", "4", "5", "6", "7"};
    private final LocalTime FIRST_PERIOD_START = LocalTime.of(8, 0);
    private final int PERIOD_DURATION = 45;
    private final int BREAK_DURATION = 5;

    public AdminClassScheduleController() {
        scheduleDAO = new ScheduleDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBoxes
        gradeComboBox.setItems(FXCollections.observableArrayList("1", "10", "11", "12"));
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));
        academicYearComboBox.setItems(FXCollections.observableArrayList("2023/24", "2024/25"));

        // Initialize TableView columns
        initializeTableColumns();

        // Hide conflict box initially
        conflictBox.setVisible(false);

        // Add listeners for combo box changes
        gradeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateGenerateButtonState();
        });
        sectionComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateGenerateButtonState();
        });
        academicYearComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateGenerateButtonState();
        });

        // Initialize button states
        updateGenerateButtonState();
    }

    private void initializeTableColumns() {
        colPeriod.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("period")));
        colMonday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Monday")));
        colTuesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Tuesday")));
        colWednesday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Wednesday")));
        colThursday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Thursday")));
        colFriday.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get("Friday")));
    }

    private void updateGenerateButtonState() {
        boolean allSelected = gradeComboBox.getValue() != null &&
                sectionComboBox.getValue() != null &&
                academicYearComboBox.getValue() != null;
        generateBtn.setDisable(!allSelected);
    }

    @FXML
    private void handleGenerateSchedule() {
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();

        // Clear existing schedule
        scheduleDAO.deleteSchedule(grade, section, academicYear);

        List<String> allConflicts = new ArrayList<>();
        List<Map<String, String>> scheduleData = new ArrayList<>();

        // Get all courses for this grade
        List<Map<String, Object>> courses = scheduleDAO.getCoursesForGrade(grade);
        if (courses.isEmpty()) {
            showAlert("Error", "No courses found for this grade.", Alert.AlertType.ERROR);
            return;
        }

        // Track course distribution
        Map<String, Integer> courseScheduleCount = new HashMap<>();
        for (Map<String, Object> course : courses) {
            courseScheduleCount.put((String) course.get("course_id"), 0);
        }

        // Generate schedule for each period and day
        for (String periodNumber : PERIODS) {
            Map<String, String> rowData = new HashMap<>();
            LocalTime startTime = FIRST_PERIOD_START.plusMinutes((Integer.parseInt(periodNumber) - 1) * (PERIOD_DURATION + BREAK_DURATION));
            LocalTime endTime = startTime.plusMinutes(PERIOD_DURATION);

            rowData.put("period", String.format("Period %s\n%s-%s",
                    periodNumber,
                    startTime.toString(),
                    endTime.toString()));

            for (String day : DAYS) {
                // Sort courses by frequency (least scheduled first)
                List<Map<String, Object>> sortedCourses = new ArrayList<>(courses);
                sortedCourses.sort((c1, c2) -> {
                    String id1 = (String) c1.get("course_id");
                    String id2 = (String) c2.get("course_id");
                    return courseScheduleCount.get(id1).compareTo(courseScheduleCount.get(id2));
                });

                boolean slotFilled = false;
                String conflictMessage = "";

                // Try each course until we find one that works
                for (Map<String, Object> course : sortedCourses) {
                    String courseId = (String) course.get("course_id");
                    String courseName = (String) course.get("course_name");

                    // Get available teachers for this course
                    List<Teacher> availableTeachers = scheduleDAO.getAvailableTeachersForCourse(
                            day, periodNumber, academicYear, courseId, grade);

                    if (!availableTeachers.isEmpty()) {
                        // Select a random teacher from available ones
                        Teacher selectedTeacher = availableTeachers.get(new Random().nextInt(availableTeachers.size()));

                        // Create schedule
                        Schedule schedule = new Schedule(
                                null, // schedule_id will be generated by DB
                                grade,
                                section,
                                selectedTeacher.getTeacherId(),
                                courseId,
                                day,
                                periodNumber,
                                startTime.toString(),
                                endTime.toString(),
                                academicYear
                        );

                        // Check for conflicts
                        List<String> conflicts = scheduleDAO.getConflicts(schedule);
                        if (conflicts.isEmpty()) {
                            // Save the schedule
                            scheduleDAO.saveSchedule(schedule);
                            rowData.put(day, String.format("%s\n%s\n%s",
                                    courseName,
                                    selectedTeacher.getFullName(),
                                    selectedTeacher.getEmail()));
                            courseScheduleCount.put(courseId, courseScheduleCount.get(courseId) + 1);
                            slotFilled = true;
                            break;
                        } else {
                            conflictMessage = String.join("\n", conflicts);
                        }
                    }
                }

                if (!slotFilled) {
                    rowData.put(day, "No suitable slot found");
                    if (!conflictMessage.isEmpty()) {
                        allConflicts.add(String.format("Could not schedule %s Period %s: %s",
                                day, periodNumber, conflictMessage));
                    }
                }
            }
            scheduleData.add(rowData);
        }

        // Update the TableView
        scheduleTableView.setItems(FXCollections.observableArrayList(scheduleData));

        // Show conflicts if any
        if (!allConflicts.isEmpty()) {
            conflictBox.setVisible(true);
            conflictListView.setItems(FXCollections.observableArrayList(allConflicts));
        } else {
            conflictBox.setVisible(false);
        }

        // Show course distribution
        StringBuilder summary = new StringBuilder("Course distribution:\n");
        for (Map<String, Object> course : courses) {
            String courseId = (String) course.get("course_id");
            String courseName = (String) course.get("course_name");
            summary.append(String.format("%s: %d periods per week\n",
                    courseName, courseScheduleCount.get(courseId)));
        }
        showAlert("Schedule Generated", summary.toString(), Alert.AlertType.INFORMATION);
    }


    @FXML
    private void handleClearSchedule() {
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String academicYear = academicYearComboBox.getValue();

        if (grade == null || section == null || academicYear == null) {
            showAlert("Input Error", "Please select grade, section, and academic year.", Alert.AlertType.WARNING);
            return;
        }

        scheduleDAO.deleteSchedule(grade, section, academicYear);
        scheduleTableView.getItems().clear();
        conflictBox.setVisible(false);
        showAlert("Success", "Schedule cleared successfully!", Alert.AlertType.INFORMATION);
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
