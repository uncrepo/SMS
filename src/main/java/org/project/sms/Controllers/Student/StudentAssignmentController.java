package org.project.sms.Controllers.Student;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import org.project.sms.Models.Model;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.OptionsDAO;
import org.project.sms.dao.SettingDAO;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentAssignmentController implements Initializable {
    public ComboBox<String> subjectsComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> semesterComboBox;
    public TextArea individualAssignmentField;
    public TextArea groupAssignmentField;

    public DatePicker indivDeadlineDate;
    public DatePicker groupDeadlineDate;

    private static final String studentId = Model.getInstance().getCurrentStudent().getStudentId();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCommonListener();
        initOptions();
        initAssign();

    }

    private void initAssign() {
        indivDeadlineDate.setValue(LocalDate.parse("2023-04-12"));
        individualAssignmentField.setText("Write about the holidays and \n" +
                "what you did with your parents. ( 10 pts )");
}


    private void initOptions() {

        academicYearComboBox.setItems(FXCollections.observableArrayList(CalendarDAO.getAllCalendar()));

        semesterComboBox.getItems().addAll("1","2");
    }

    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadAssignmentsForStudent();
        gradeComboBox.valueProperty().addListener(commonListener);
        subjectsComboBox.valueProperty().addListener(commonListener);
        sectionComboBox.valueProperty().addListener(commonListener);
        academicYearComboBox.valueProperty().addListener(commonListener);
        semesterComboBox.valueProperty().addListener(commonListener);
    }

    private void loadAssignmentsForStudent() {
        String academicYear = academicYearComboBox.getValue();

        String grade = OptionsDAO.getStudentGrade(studentId,academicYear);
        gradeComboBox.setValue(grade);

        String section = OptionsDAO.getStudentSection(studentId,academicYear);
        sectionComboBox.setValue(section);

        ObservableList<String> courses = FXCollections.observableArrayList(OptionsDAO.getStudentCourses(studentId,academicYear));
        if (subjectsComboBox.getValue() == null) {
            subjectsComboBox.setItems(courses);
        }

        if (subjectsComboBox.getValue() != null) {
            String course = subjectsComboBox.getValue();
            fetchAndDisplayAssignment(academicYear,grade,section,course);
        }

    }

    private void fetchAndDisplayAssignment( String academicYear, String grade, String section, String course) {

        if (academicYear == null || grade == null || section == null || course == null) return;

        String query = """
        SELECT ac.individual_assignment, ac.group_assignment,ac.indivAsgn_deadline, ac.grpAsgn_deadline
        FROM assignment_course ac
        JOIN grade_course gc ON ac.grade_course_id = gc.grade_course_id
        JOIN courses c ON gc.course_id = c.course_id
        JOIN classes cls ON ac.class_id = cls.class_id
        JOIN grades g ON cls.grade_id = g.grade_id
        WHERE cls.academic_year = ? AND g.grade = ? AND cls.section = ? AND c.course_name = ?
    """;

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, academicYear);
            stmt.setString(2, grade);
            stmt.setString(3, section);
            stmt.setString(4, course);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date indivDeadline = rs.getDate("indivAsgn_deadline");
                Date grpDeadline = rs.getDate("grpAsgn_deadline");
                if (indivDeadline != null) {
                    indivDeadlineDate.setValue(indivDeadline.toLocalDate());
                }
                if (grpDeadline != null) {
                    groupDeadlineDate.setValue(grpDeadline.toLocalDate());
                }
                individualAssignmentField.setText(rs.getString("individual_assignment"));
                individualAssignmentField.setText(rs.getString("group_assignment"));
            } else {
                individualAssignmentField.clear();
                indivDeadlineDate.setValue(null);
                groupAssignmentField.clear();
                groupDeadlineDate.setValue(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
