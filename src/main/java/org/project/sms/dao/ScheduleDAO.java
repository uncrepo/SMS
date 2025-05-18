package org.project.sms.dao;

import org.project.sms.Models.Model;
import org.project.sms.Models.Schedule;
import org.project.sms.Models.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ScheduleDAO {

    public List<Map<String, Object>> getCoursesForGrade(String grade) {
        List<Map<String, Object>> courses = new ArrayList<>();
        String query = "SELECT c.course_id, c.course_name " +
                "FROM Courses c " +
                "JOIN grade_course gc ON c.course_id = gc.course_id " +
                "JOIN Grades g ON gc.grade_id = g.grade_id " +
                "WHERE g.grade_id =  ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, grade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("course_id", rs.getString("course_id"));
                course.put("course_name", rs.getString("course_name"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Teacher> getAvailableTeachersForCourse(String day, String period, String academicYear,
                                                       String courseId, String grade) {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT DISTINCT t.* , users.full_name" +
                " FROM Teachers t " +
                " JOIN users ON t.user_id = users.user_id " +
                "JOIN assign_teacher_class tc ON t.teacher_id = tc.teacher_id " +
                "JOIN grade_course gc ON tc.grade_course_id = tc.grade_course_id " +
                "JOIN courses co ON gc.course_id = co.course_id " +
                "WHERE co.course_id = ? " +
                "AND t.teacher_id NOT IN ( " +
                "    SELECT s.teacher_id " +
                "    FROM Schedules s " +
                "    WHERE s.day_of_week = ? " +
                "    AND s.period_number = ? " +
                "    AND s.academic_year = ? " +
                ") ";


        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseId);
            stmt.setString(2, day);
            stmt.setString(3, period);
            stmt.setString(4, academicYear);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian")
                        );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public List<String> getConflicts(Schedule schedule) {
        List<String> conflicts = new ArrayList<>();

        // Check for teacher conflicts
        String teacherQuery = "SELECT g.grade, s.section " +
                "FROM Schedules s " +
                "JOIN Grades g ON s.grade_id = g.grade_id " +
                "WHERE s.teacher_id = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(teacherQuery)) {
            stmt.setString(1, schedule.getTeacherId());
            stmt.setString(2, schedule.getDayOfWeek());
            stmt.setString(3, schedule.getPeriodNumber());
            stmt.setString(4, schedule.getAcademicYear());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                conflicts.add(String.format("Teacher already has class in Grade %s Section %s during this period",
                        rs.getString("grade"), rs.getString("section")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check for class conflicts
        String classQuery = "SELECT c.course_name, u.full_name " +
                "FROM Schedules s " +
                "JOIN Courses c ON s.course_id = c.course_id " +
                "JOIN Teachers t ON s.teacher_id = t.teacher_id " +
                "JOIN Users u ON s.teacher_id = u.user_id " +
                "WHERE s.grade_id = ? " +
                "AND s.section = ? " +
                "AND s.day_of_week = ? " +
                "AND s.period_number = ? " +
                "AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(classQuery)) {
            stmt.setString(1, schedule.getGradeId());
            stmt.setString(2, schedule.getSection());
            stmt.setString(3, schedule.getDayOfWeek());
            stmt.setString(4, schedule.getPeriodNumber());
            stmt.setString(5, schedule.getAcademicYear());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                conflicts.add(String.format("Class already has %s with %s during this period",
                        rs.getString("course_name"),
                        rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conflicts;
    }

    public void saveSchedule(Schedule schedule) {
        String query = "INSERT INTO Schedules (grade_id, section, teacher_id, course_id, " +
                "day_of_week, period_number, start_time, end_time, academic_year) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, schedule.getGradeId());
            stmt.setString(2, schedule.getSection());
            stmt.setString(3, schedule.getTeacherId());
            stmt.setString(4, schedule.getCourseId());
            stmt.setString(5, schedule.getDayOfWeek());
            stmt.setString(6, schedule.getPeriodNumber());
            stmt.setString(7, schedule.getStartTime());
            stmt.setString(8, schedule.getEndTime());
            stmt.setString(9, schedule.getAcademicYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSchedule(String grade, String section, String academicYear) {
        String query = "DELETE FROM Schedules " +
                "WHERE grade_id = (SELECT grade_id FROM Grades WHERE grade = ?) " +
                "AND section = ? " +
                "AND academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, grade);
            stmt.setString(2, section);
            stmt.setString(3, academicYear);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getTeacherScheduleSummary(int teacherId, String academicYear) {
        Map<String, Object> summary = new HashMap<>();
        String query = "SELECT " +
                "COUNT(DISTINCT CONCAT(s.grade_id, s.section, s.course_id)) as total_classes, " +
                "COUNT(DISTINCT s.course_id) as total_courses, " +
                "COUNT(DISTINCT sc.student_id) as total_students " +
                "FROM Schedules s " +
                "JOIN Classes c ON (s.grade_id = c.grade_id AND s.section = c.section " +
                "                   AND s.academic_year = c.academic_year) " +
                "LEFT JOIN student_class sc ON c.class_id = sc.class_id " +
                "WHERE s.teacher_id = ? AND s.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            stmt.setString(2, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                summary.put("total_classes", rs.getInt("total_classes"));
                summary.put("total_courses", rs.getInt("total_courses"));
                summary.put("total_students", rs.getInt("total_students"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    public Map<String, Object> getStudentScheduleSummary(int studentId, String academicYear) {
        Map<String, Object> summary = new HashMap<>();
        String query = "SELECT " +
                "g.grade, c.section, " +
                "t.first_name as teacher_first_name, " +
                "t.last_name as teacher_last_name " +
                "FROM Students s " +
                "JOIN Classes c ON s.class_id = c.class_id " +
                "JOIN Grades g ON c.grade_id = g.grade_id " +
                "JOIN Teachers t ON c.class_teacher_id = t.teacher_id " +
                "WHERE s.student_id = ? AND c.academic_year = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, academicYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                summary.put("grade", rs.getString("grade"));
                summary.put("section", rs.getString("section"));
                summary.put("class_teacher", rs.getString("teacher_first_name") + " " +
                        rs.getString("teacher_last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }
}