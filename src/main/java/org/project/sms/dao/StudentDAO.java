package org.project.sms.dao;

import org.project.sms.Models.Model;
import org.project.sms.Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student getStudentByUserId(String userId) {
        String query = "SELECT * FROM Students WHERE user_id = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getString("student_id"),
                        rs.getString("department"),
                        rs.getString("section"),
                        rs.getString("current_year"));
                return student;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getStudentsByDepartmentYearSection(String department, String year, String section) {
        List<Student> students = new ArrayList<>();
        String query = " SELECT " +
                "    Users.full_name," +
                "    Students.student_id," +
                "    Students.current_year," +
                "    Students.department," +
                "    Students.section FROM Users JOIN " +
                "    Students ON Users.user_id = Students.user_id  WHERE department = ? AND current_year = ? AND Students.section = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, department);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("department"),
                        rs.getString("section"),
                        rs.getString("current_year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

}
