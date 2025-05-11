package org.project.sms.dao;

import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionsDAO {

    public static List<String> getAllGrades(String teacherId) {
        List<String> grades = new ArrayList<>();
        String query = """
        SELECT Grades.grade FROM assign_teacher_class 
            JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id
            JOIN Grades ON grade_course.grade_id = Grades.grade_id
            where assign_teacher_class.teacher_id = ?
""";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
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

    public static List<String> getAllCourses(String teacherId) {
        List<String> courses = new ArrayList<>();
        String query = """
        SELECT Courses.course_name FROM assign_teacher_class 
            JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id
            JOIN Grades ON grade_course.grade_id = Grades.grade_id
            JOIN Courses ON grade_course.course_id = Courses.course_id
            where assign_teacher_class.teacher_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(
                        rs.getString("course_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static List<String> getAllSections(String teacherId) {
        List<String> sections = new ArrayList<>();
        String query = """
        SELECT Classes.section FROM assign_teacher_class 
            JOIN Classes ON assign_teacher_class.class_id = Classes.class_id
            where assign_teacher_class.teacher_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sections.add(
                        rs.getString("section")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public static List<String> getAllAcademicYears(String teacherId) {
        List<String> academicYears = new ArrayList<>();
        String query = """
        SELECT Classes.academic_year FROM assign_teacher_class 
            JOIN Classes ON assign_teacher_class.class_id = Classes.class_id
            where assign_teacher_class.teacher_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                academicYears.add(
                        rs.getString("academic_year")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return academicYears;
    }

    public static List<String> getAllSemesters(String teacherId) {
        List<String> semesters = new ArrayList<>();
        String query = """
        SELECT DISTINCT Results.semester FROM Results 
            JOIN Teachers ON Results.teacher_id = Teachers.teacher_id
            where Teachers.teacher_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                semesters.add(
                        rs.getString("semester")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesters;
    }
}

