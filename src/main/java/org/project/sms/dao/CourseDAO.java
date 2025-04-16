package org.project.sms.dao;

import org.project.sms.Models.Course;
import org.project.sms.Models.Model;
import org.project.sms.Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getCoursesByDepartmentYearSection(String department, String year, String section) {
        List<Course> courses = new ArrayList<>();
        String query = " SELECT " +
                "    Users.full_name," +
                "    Courses.course_title," +
                "    Courses.course_code," +
                "    Courses.credit_hour, " +
                "    Courses.course_id "+
                "    FROM Users " +
                "    JOIN Teachers ON Users.user_id = Teachers.user_id " +
                "    JOIN Courses ON Teachers.course_id = Courses.course_id  WHERE department = ? AND year = ? AND Teachers.section = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, department);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("course_id"),
                        rs.getString("course_title"),
                        rs.getString("course_code"),
                        rs.getString("credit_hour"),
                        rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
