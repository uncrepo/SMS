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

    public static Student getStudentByUserId(String userId) {
        String query = "SELECT * FROM Students WHERE user_id = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getString("student_id"),
                        rs.getString("class_id"),
                        rs.getString("grade_id"),
                        rs.getString("section"),
                        rs.getString("academic_year"));
                return student;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Student> getStudentsByGradeYearSection(String grade, String year, String section) {
        List<Student> students = new ArrayList<>();

        String query = " SELECT \n" +
                "    Users.full_name," +
                "    Students.student_id," +
                "    Users.phone," +
                "    Users.email," +
                "    Users.guardian " +
                "FROM  " +
                "    Users " +
                "JOIN " +
                "    Students ON Users.user_id = Students.user_id  WHERE grade_id = ? AND academic_year = ? AND section = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Student> getStudentsClassResultsByGradeYearSection(String grade, String year, String section) {
        List<Student> students = new ArrayList<>();

        String query = " SELECT \n" +
                "    Users.full_name," +
                "    Students.student_id," +
                "    Averages.average," +
                "    Students.academic_year," +
                "    Grades.grade," +
                "    Averages.section," +
                "    Averages.comment," +
                "    Users.guardian " +
                "FROM  " +
                "    Users " +
                "    JOIN Averages ON Averages.student_id = Students.student_id " +
                "    JOIN Grades ON Grades.grade_id = Teachers.grade_id " +
                "    JOIN Students ON Users.user_id = Students.user_id  WHERE grade_id = ? AND academic_year = ? AND section = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
