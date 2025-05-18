package org.project.sms.Controllers.Teacher;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import org.project.sms.Models.Model;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.OptionsDAO;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeacherAssignmentController implements Initializable {


    public ComboBox <String>academicYearComboBox;
    public ComboBox <String>semesterComboBox;
    public ComboBox <String>gradeComboBox;
    public ComboBox <String>sectionComboBox;
    public ComboBox <String>courseComboBox;
    public TextArea indivAsgnField;
    public TextArea groupAsgnField;

    public DatePicker grpDeadlineDate;
    public DatePicker indivDeadlineDate;

    private static final String teacherId = Model.getInstance().getCurrentTeacher().getTeacherId();
    public Button updateAsgnBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
        setupCommonListener();
        BtnClicks();
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
        WHERE cls.academic_year = ? AND g.grade_name = ? AND cls.section = ? AND c.course_name = ? AND atc.teacher_id = ?
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
        WHERE cls.academic_year = ? AND g.grade_name = ? AND cls.section = ? AND c.course_name = ? AND atc.teacher_id = ?
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
