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

public class CourseDAO {
    public static List<Course> getCoursesByGradeYearSection(String grade, String year, String section) {
        List<Course> courses = new ArrayList<>();
        String query ="  SELECT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.teacher_id," +
                "    Teachers.phone, " +
                "    Teachers.guardian, " +
                "    Teachers.email, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name " +
                "FROM assign_teacher_class " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id "+
                "JOIN Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN Users ON teachers.user_id = Users.user_id " +
                "JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id " +
                " WHERE Grades.grade LIKE ? AND Classes.section LIKE ? AND Classes.academic_year LIKE ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, grade);
            stmt.setString(2, section);
            stmt.setString(3, year);
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

    public static List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();
        String query = "SELECT DISTINCT " +
                "    Courses.course_name " +
                "FROM " +
                "    grade_course " +
                "JOIN " +
                "Courses ON grade_course.course_id = Courses.course_id";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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


    public static List<String> getAllCoursesByGrade(String grade) {
        List<String> courses = new ArrayList<>();
        String query = "SELECT DISTINCT " +
                "    Courses.course_name " +
                "FROM " +
                "    grade_course " +
                "JOIN " +
                "Courses ON grade_course.course_id = Courses.course_id WHERE grade_id = ? ";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
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

    public static String getCourseID(String course) {
        String courseId = null;
        String query = "SELECT course_id FROM Courses where course_name = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, course);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseId = rs.getString("course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseId;
    }


}

