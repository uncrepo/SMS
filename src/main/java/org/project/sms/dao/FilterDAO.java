package org.project.sms.dao;

import org.project.sms.Models.Model;
import org.project.sms.Models.NotAssignedStudent;
import org.project.sms.Models.Result;
import org.project.sms.Models.Student;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

}
