package org.project.sms.dao;

import org.project.sms.Models.Model;
import org.project.sms.Models.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public static List<Result> getCurrentActiveClass(String classId,String semester, String gradeCourseId, String teacherId) {
        List<Result> results = new ArrayList<>();
        String query = """
                SELECT 
                Users.full_name,
				students.student_id,
				Results.result_id,
                Results.attendance,
                Results.individual_assignment,
                Results.group_assignment,
                Results.mid_exam,
                Results.final_exam,
                Results.total_result,
                Results.teacher_id,
                Results.class_id
                FROM  Results
                JOIN Classes ON Results.class_id = Classes.class_id
                JOIN assign_teacher_class ON Results.teacher_id = assign_teacher_class.teacher_id
                JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id
                JOIN Students ON Results.student_id = Students.student_id
                JOIN Users ON students.user_id = Users.user_id where grade_course.grade_course_id = ? AND Results.semester = ? AND 
                                                                     Results.class_id = ? AND Results.teacher_id = ?

""";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, gradeCourseId);
            stmt.setString(2, semester);
            stmt.setString(3, classId);
            stmt.setString(4, teacherId);


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Result result = new Result(
                            rs.getString("result_id"),
                            rs.getString("full_name"),
                            rs.getString("attendance"),
                            rs.getString("individual_assignment"),
                            rs.getString("group_assignment"),
                            rs.getString("mid_exam"),
                            rs.getString("final_exam"),
                            rs.getString("total_result")
                    );
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void updateResult(String resultId, String attendance, String indivAsgn, String groupAsgn, String midExam, String finalExam) {
        String query = """ 
        UPDATE results SET attendance = ?, individual_assignment = ?, group_assignment = ?,mid_exam = ?, final_exam = ? WHERE result_id = ?
""";
        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, attendance);
            stmt.setString(2, indivAsgn);
            stmt.setString(3, groupAsgn);
            stmt.setString(4, midExam);
            stmt.setString(5, finalExam);
            stmt.setString(6, resultId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            }
        }
}
