package org.project.sms.dao;

import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionsDAO {

    private static final String currentAcademicYear = Model.getInstance().getCurrentAcademicYear();


    public static List<String> getAllGrades(String teacherId) {
        List<String> grades = new ArrayList<>();
        String query = """
        SELECT DISTINCT Grades.grade FROM assign_teacher_class 
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
        SELECT DISTINCT Courses.course_name FROM assign_teacher_class 
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
        SELECT DISTINCT Classes.section FROM assign_teacher_class 
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
            where assign_teacher_class.teacher_id = ? AND classes.academic_year = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherId);
            stmt.setString(2, currentAcademicYear);

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
        return null;
    }

    public static List<String> getStudentGrade(String studentId) {
        List<String> grades = new ArrayList<>();
        String query = """
        SELECT DISTINCT Grades.grade FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            JOIN Grades ON Classes.grade_id = Grades.grade_id
            where student_class.student_id = ?
""";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
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

    public static String getStudentGrade(String studentId,String academicYear) {
        String grade = null;
        String query = """
        SELECT DISTINCT Grades.grade FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            JOIN Grades ON Classes.grade_id = Grades.grade_id
            where student_class.student_id = ? AND classes.academic_year = ?
""";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            stmt.setString(2, academicYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grade = rs.getString("grade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }

    public static String getStudentSection(String studentId,String academicYear) {
        String section = null;
        String query = """
        SELECT DISTINCT Classes.section FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            JOIN Grades ON Classes.grade_id = Grades.grade_id
            where student_class.student_id = ? AND classes.academic_year = ?
""";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            stmt.setString(2, academicYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                section = rs.getString("section");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }


    public static List<String> getStudentCourses(String studentId) {
        List<String> courses = new ArrayList<>();
        String query = """
        SELECT DISTINCT Courses.course_name FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            JOIN Grades ON classes.grade_id = Grades.grade_id
            JOIN grade_course ON Classes.grade_id = grade_course.grade_id
            JOIN Courses ON grade_course.course_id = Courses.course_id
            where student_class.student_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
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


    public static List<String> getStudentCourses(String studentId,String academicYear) {
        List<String> courses = new ArrayList<>();
        String query = """
        SELECT DISTINCT Courses.course_name FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            JOIN Grades ON classes.grade_id = Grades.grade_id
            JOIN grade_course ON Classes.grade_id = grade_course.grade_id
            JOIN Courses ON grade_course.course_id = Courses.course_id
            where student_class.student_id = ? AND Classes.academic_year = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            stmt.setString(2, academicYear);
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

    public static List<String> getStudentSection(String studentId) {
        List<String> sections = new ArrayList<>();
        String query = """
        SELECT DISTINCT Classes.section FROM student_class 
            JOIN Classes ON student_class.class_id = Classes.class_id
            where student_class.student_id = ?
""";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);

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

    }





