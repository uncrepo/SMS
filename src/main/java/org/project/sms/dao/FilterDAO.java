package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.*;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class FilterDAO {
    public static List<Student> getStudentsByFilter(String year, String grade, String section, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT   " +
                "             Users.full_name,  " +
                "             student_class.student_class_id, " +
                "             Classes.section, " +
                "             Classes.academic_year, " +
                "             Grades.grade " +
                "               FROM  student_class " +
                "               JOIN Classes ON student_class.class_id = Classes.class_id " +
                "               JOIN Students ON student_class.student_id = Students.student_id " +
                "               JOIN Users ON students.user_id = Users.user_id " +
                "               JOIN Grades ON Classes.grade_id = Grades.grade_id WHERE 1=1 ");

        if (year != null && !year.isEmpty()) {
            query.append("AND Classes.academic_year = ? ");
            params.add(year);
        }
        if (grade != null && !grade.isEmpty()) {
            query.append("AND grades.grade = ? ");
            params.add(grade);
        }
        if (section != null && !section.isEmpty()) {
            query.append("AND Classes.section = ? ");
            params.add(section);
        }

        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_class_id"),
                        rs.getString("full_name"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("section"),
                        rs.getString("student_class_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<NotAssignedStudent> getNotAssignedStudentsByFilter(int limit, int offset) {
        List<NotAssignedStudent> students = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT  " +
                "    Users.full_name, " +
                "    students.student_id, "+
                "    last_year_record.average, " +
                "    last_year_record.comment, " +
                "    Grades.grade, " +
                "    Classes.academic_year " +
                "FROM  last_year_record " +
                "JOIN student_class ON last_year_record.student_class_id = student_class.student_class_id "+
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Classes ON student_class.class_id = classes.class_id " +
                "JOIN Users ON students.user_id = Users.user_id " +
                "JOIN Grades ON Classes.grade_id = Grades.grade_id " +
                "WHERE last_year_record.assigned_next_grade = 'NO' AND 1=1 ");


        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new NotAssignedStudent(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("comment"),
                        rs.getString("grade"),
                        rs.getString("average"),
                        rs.getString("academic_year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Result> getStudentsResultsForAdminByFilter(String year, String grade, String section, int limit, int offset) {
        List<Result> students = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT  " +
                "    Users.full_name, " +
                "    students.student_id, "+
                "    last_year_record.average, " +
                "    last_year_record.comment, " +
                "    Grades.grade, " +
                "    Classes.academic_year " +
                "FROM  last_year_record " +
                "JOIN student_class ON last_year_record.student_class_id = student_class.student_class_id "+
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Classes ON student_class.class_id = classes.class_id " +
                "JOIN Users ON students.user_id = Users.user_id " +
                "JOIN Grades ON Classes.grade_id = Grades.grade_id ");

        if (year != null && !year.isEmpty()) {
            query.append("WHERE Classes.academic_year = ? ");
            params.add(year);
        }
        if (grade != null && !grade.isEmpty()) {
            query.append("AND grades.grade = ? ");
            params.add(grade);
        }
        if (section != null && !section.isEmpty()) {
            query.append("AND Classes.section = ? ");
            params.add(section);
        }

        query.append(" ORDER BY average DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Result(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("average"),
                        rs.getString("comment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Student> getAllStudentDetailsByFilter(String year, String grade, String section, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT  " +
                "    Users.full_name, " +
                "    student_class.student_class_id," +
                "    Students.phone, " +
                "    Students.guardian, " +
                "    Students.email, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade " +
                "FROM student_class " +
                "JOIN Classes ON student_class.class_id = Classes.class_id "+
                "JOIN Students ON student_class.student_id =  Students.student_id " +
                "JOIN Users ON Students.user_id = Users.user_id " +
                "JOIN Grades ON Classes.grade_id = Grades.grade_id WHERE 1=1");

        if (year != null && !year.isEmpty()) {
            query.append("AND Classes.academic_year = ? ");
            params.add(year);
        }
        if (grade != null && !grade.isEmpty()) {
            query.append("AND grades.grade = ? ");
            params.add(grade);
        }
        if (section != null && !section.isEmpty()) {
            query.append("AND Classes.section = ? ");
            params.add(section);
        }

        query.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_class_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("section")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Teacher> getAllTeachersDetailsByFilter(String academicYear, String grade, String section,
                                                              int limit, int offset) {
        List<Teacher> teachers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT  " +
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
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id WHERE 1=1 ");

        if (academicYear != null && !academicYear.isEmpty()) {
            query.append("AND Classes.academic_year = ? ");
            params.add(academicYear);
        }
        if (grade != null && !grade.isEmpty()) {
            query.append("AND grades.grade = ? ");
            params.add(grade);
        }
        if (section != null && !section.isEmpty()) {
            query.append("AND Classes.section = ? ");
            params.add(section);
        }

        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("course_name"),
                        rs.getString("section")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static List<Student> getAllStudentsDetailsForEditByFilter(int limit, int offset) {
        List<Student> students = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder(" SELECT " +
                "    Users.full_name, " +
                "    Students.student_id, " +
                "    Students.guardian, " +
                "    Students.phone, " +
                "    Students.email, " +
                "    Students.gender, "+
                "    Users.status "+
                "    FROM Users " +
                "    JOIN Students ON Users.user_id = Students.user_id WHERE 1=1 ");

        // Add ORDER BY clause dynamically
//        if (orderBy != null && !orderBy.isEmpty()) {
//            query.append("ORDER BY ").append(orderBy).append(" ");
//            if (orderDir != null && (orderDir.equalsIgnoreCase("ASC") || orderDir.equalsIgnoreCase("DESC"))) {
//                query.append(orderDir).append(" ");
//            } else {
//                query.append("ASC "); // default
//            }
//        }

        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Teacher> getAllTeachersDetailsForEditByFilter(int limit, int offset) {
        List<Teacher> teachers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder(" SELECT " +
                "    Users.full_name, " +
                "    Teachers.teacher_id, " +
                "    Teachers.guardian, " +
                "    Teachers.phone, " +
                "    Teachers.email, " +
                "    Teachers.gender, "+
                "    Users.status "+
                "    FROM Users " +
                "    JOIN Teachers ON Users.user_id = Teachers.user_id WHERE 1=1 ");

        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static List<AssignedTeacher> getAssignedTeachersByFilter(String year, String grade, String section, int limit, int offset) {
        List<AssignedTeacher> teachers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder("  SELECT DISTINCT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.assign_teacher_id, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name, " +
                "    Results.semester " +
                "FROM  " +
                "    assign_teacher_class " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id "+
                "JOIN  " +
                "    Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN  " +
                "    Users ON teachers.user_id = Users.user_id " +
                "JOIN " +
                "    grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN  " +
                " Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id " +
                "JOIN Results ON assign_teacher_class.teacher_id = Results.teacher_id WHERE 1=1");

        if (year != null && !year.isEmpty()) {
            query.append("AND Classes.academic_year = ? ");
            params.add(year);
        }
        if (grade != null && !grade.isEmpty()) {
            query.append("AND grades.grade = ? ");
            params.add(grade);
        }
        if (section != null && !section.isEmpty()) {
            query.append("AND Classes.section = ? ");
            params.add(section);
        }

        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teachers.add(new AssignedTeacher(
                        rs.getString("assign_teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("course_name"),
                        rs.getString("section"),
                        rs.getString("semester")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static List<Teacher> getNotAssignedTeachersByFilter(int limit, int offset) {
        List<Teacher> teachers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder(" SELECT " +
                "    Users.full_name, " +
                "    Teachers.teacher_id, " +
                "    Teachers.guardian, " +
                "    Teachers.phone, " +
                "    Teachers.email, " +
                " Teachers.gender,"+
                " Users.status"+
                "    FROM Users " +
                "    JOIN Teachers ON Users.user_id = Teachers.user_id ");


        query.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("gender"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}
