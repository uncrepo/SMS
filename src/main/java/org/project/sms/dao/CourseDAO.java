package org.project.sms.dao;

import org.project.sms.Models.Course;
import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public static List<Course> getCoursesByGradeYearSection(String grade, String year, String section) {
        List<Course> courses = new ArrayList<>();
        String query = " SELECT " +
                "    Users.full_name, " +
                "    Courses.course_name " +
                "FROM " +
                "    Users " +
                "JOIN " +
                "    Teachers ON Users.user_id = Teachers.user_id " +
                "JOIN " +
                " Courses ON Teachers.course_id = Courses.course_id " +
                "    WHERE grade_id = ? AND academic_year = ? AND section = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("course_name"),
                        rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
