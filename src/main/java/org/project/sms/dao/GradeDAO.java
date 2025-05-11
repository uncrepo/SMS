package org.project.sms.dao;

import org.project.sms.Models.Course;
import org.project.sms.Models.Grade;
import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public static List<String> getAllGrades() {
        List<String> grades = new ArrayList<>();
        String query = "SELECT grade FROM grades";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grades.add(
                        rs.getString("grade")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static String getGradeID(String grade) {
        String gradeId = null;
        String query = "SELECT grade_id FROM Grades where grade = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, grade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                gradeId = rs.getString("grade_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeId;
    }

    public static String getGradeCourseID(String grade, String course) {
        String gradeCourseId = null;
        String query = "SELECT grade_course_id FROM grade_course where grade_id = ? AND course_id = ?";

        String gradeId = getGradeID(grade);
        String courseId = CourseDAO.getCourseID(course);

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, gradeId);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                gradeCourseId = rs.getString("grade_course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeCourseId;
    }
}

