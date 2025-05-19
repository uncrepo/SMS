package org.project.sms.Controllers.Teacher;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.project.sms.Models.Model;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.OptionsDAO;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeacherAssignmentController implements Initializable {


    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> semesterComboBox;
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> courseComboBox;
    public TextArea indivAsgnField;
    public TextArea groupAsgnField;

    public DatePicker grpDeadlineDate;
    public DatePicker indivDeadlineDate;

    private static final String teacherId = Model.getInstance().getCurrentTeacher().getTeacherId();
    public Button updateAsgnBtn;
    public Button resetBtn;
    public VBox TeacherAssignment;

    @FXML
    private TextField titleField;
    @FXML
    private CheckBox isFileCheck;
    @FXML
    private Button attachBtn;
    @FXML
    private Label selectedFileLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button submitBtn;
    @FXML
    private Label statusLabel;


    private File selectedFile = null;


    public void initializeH() {
        attachBtn.setDisable(true);
        descriptionArea.setDisable(false);

        isFileCheck.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            attachBtn.setDisable(!isSelected);
            descriptionArea.setDisable(isSelected);
            if (!isSelected) {
                selectedFileLabel.setText("No file selected");
                selectedFile = null;
            }
        });

        attachBtn.setOnAction(e -> chooseFile());

        submitBtn.setOnAction(e -> handleSubmit());
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Assignment File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            selectedFile = file;
            selectedFileLabel.setText(file.getName());
        } else {
            selectedFileLabel.setText("No file selected");
        }
    }

    private void handleSubmit() {
        String title = titleField.getText().trim();
        boolean fileBased = isFileCheck.isSelected();

        if (title.isEmpty()) {
            statusLabel.setText("Title is required.");
            return;
        }

        if (fileBased) {
            if (selectedFile == null) {
                statusLabel.setText("Please select a file.");
                return;
            }

            // TODO: Save file to server/disk and record in DB
            System.out.println("Uploading file: " + selectedFile.getAbsolutePath());
            // e.g., fileService.saveAssignmentFile(title, selectedFile);

        } else {
            String description = descriptionArea.getText().trim();
            if (description.isEmpty()) {
                statusLabel.setText("Please provide assignment text.");
                return;
            }

            // TODO: Save text-based assignment to DB
            System.out.println("Saving text assignment: " + description);
            // e.g., assignmentDAO.saveTextAssignment(title, description);
        }

        statusLabel.setText("Assignment submitted successfully!");
        resetForm();
    }

    private void resetForm() {
        titleField.clear();
        selectedFile = null;
        selectedFileLabel.setText("No file selected");
        descriptionArea.clear();
        isFileCheck.setSelected(false);
    }

    private Stage getStage() {
        return (Stage) titleField.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
        setupCommonListener();
//        BtnClicks();
        initializeH();
    }

    private void BtnClicks() {
        updateAsgnBtn.setOnAction(e -> updateAssignmentsForTeacher());
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(OptionsDAO.getAllGrades(teacherId));
        gradeComboBox.setItems(grades);

        ObservableList<String> courses = FXCollections.observableArrayList(OptionsDAO.getAllCourses(teacherId));
        courseComboBox.setItems(courses);

        ObservableList<String> sections = FXCollections.observableArrayList(OptionsDAO.getAllSections(teacherId));
        sectionComboBox.setItems(sections);

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

        ObservableList<String> semesters = FXCollections.observableArrayList(Model.getInstance().getCurrentSemester());
        semesterComboBox.setItems(semesters);

    }

    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadAssignmentsForTeacher();
        gradeComboBox.valueProperty().addListener(commonListener);
        courseComboBox.valueProperty().addListener(commonListener);
        sectionComboBox.valueProperty().addListener(commonListener);
        academicYearComboBox.valueProperty().addListener(commonListener);
        semesterComboBox.valueProperty().addListener(commonListener);
    }

    private void loadAssignmentsForTeacher() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String course = courseComboBox.getValue();
        fetchAndDisplayAssignment(academicYear,grade,section,course);


    }

    private void updateAssignmentsForTeacher() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();
        String course = courseComboBox.getValue();

        String individualAssignment = indivAsgnField.getText();
        String groupAssignment = groupAsgnField.getText();
        LocalDate gDeadline = grpDeadlineDate.getValue();
        LocalDate iDeadline = indivDeadlineDate.getValue();

        updateAssignment(academicYear,grade,section,course, individualAssignment,groupAssignment,iDeadline,gDeadline);
        loadAssignmentsForTeacher();
    }

    private void fetchAndDisplayAssignment( String academicYear, String grade, String section, String course) {

        if (academicYear == null || grade == null || section == null || course == null) return;

        String query = """
        SELECT ac.individual_assignment, ac.group_assignment,ac.indivAsgn_deadline, ac.grpAsgn_deadline
        FROM assignment_course ac
        JOIN grade_course gc ON ac.grade_course_id = gc.grade_course_id
        JOIN assign_teacher_class atc ON ac.teacher_id = atc.teacher_id
        JOIN courses c ON gc.course_id = c.course_id
        JOIN classes cls ON ac.class_id = cls.class_id
        JOIN grades g ON cls.grade_id = g.grade_id
        WHERE cls.academic_year = ? AND g.grade = ? AND cls.section = ? AND c.course_name = ? AND atc.teacher_id = ?
    """;

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, academicYear);
            stmt.setString(2, grade);
            stmt.setString(3, section);
            stmt.setString(4, course);
            stmt.setString(5, teacherId);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date indivDeadline = rs.getDate("indivAsgn_deadline");
                Date grpDeadline = rs.getDate("grpAsgn_deadline");
                if (indivDeadline != null) {
                    indivDeadlineDate.setValue(indivDeadline.toLocalDate());
                }
                if (grpDeadline != null) {
                    grpDeadlineDate.setValue(grpDeadline.toLocalDate());
                }
                indivAsgnField.setText(rs.getString("individual_assignment"));
                groupAsgnField.setText(rs.getString("group_assignment"));
            } else {
                indivAsgnField.clear();
                groupAsgnField.clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateAssignment(String academicYear, String grade, String section, String course, String individualAssignment, String groupAssignment, LocalDate iDealine, LocalDate gDeadline) {
            if (academicYear == null || grade == null || section == null || course == null) return;

            String query = """
        UPDATE assignment_course ac
        JOIN grade_course gc ON ac.grade_course_id = gc.grade_course_id
        JOIN assign_teacher_class atc ON ac.teacher_id = atc.teacher_id
        JOIN courses c ON gc.course_id = c.course_id
        JOIN classes cls ON ac.class_id = cls.class_id
        JOIN grades g ON cls.grade_id = g.grade_id
        SET ac.individual_assignment = ?, ac.group_assignment = ?,ac.indivAsgn_deadline = ?, ac.grpAsgn_deadline = ?
        WHERE cls.academic_year = ? AND g.grade = ? AND cls.section = ? AND c.course_name = ? AND atc.teacher_id = ?
    """;

            try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

                stmt.setString(1, individualAssignment);
                stmt.setString(2, groupAssignment);
                stmt.setDate(3, Date.valueOf(iDealine));
                stmt.setDate(4, Date.valueOf(gDeadline));

                stmt.setString(5, academicYear);
                stmt.setString(6, grade);
                stmt.setString(7, section);
                stmt.setString(8, course);
                stmt.setString(9, teacherId);

                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();

            }

}


}
