package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.Model;
import org.project.sms.Models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentDAO {

    public Student getStudentByUserId(String userId) {
        String query = "SELECT users.full_name, students.email,students.students_id, students.phone, students.guardian" +
                " FROM users JOIN students on users.user_id = students.user_id WHERE Users.user_id = ? ";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone"));
                return student;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ObservableList<Student> getAllStudents() {
        ObservableList<Student> teachers = FXCollections.observableArrayList();
        String query = " SELECT \n" +
                "    Users.full_name, " +
                "    Students.student_id, " +
                "    Students.guardian, " +
                "    Students.phone, " +
                "    Students.email " +
                "    FROM Users " +
                "    JOIN Students ON Users.user_id = Students.user_id ";

        try (Statement stmt = Model.getInstance().getDbConnection().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone")
                );
                teachers.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
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
                "    student_class " +
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Users ON Users.user_id = Students.user_id  WHERE grade_id = ? AND academic_year = ? AND section = ?";
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
                        rs.getString("full_name")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public static List<Student> getAllStudentsDefault() {
        List<Student> students = new ArrayList<>();
        String query = "  SELECT  " +
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
                "JOIN Grades ON Classes.grade_id = Grades.grade_id ";

        try (Statement stmt = Model.getInstance().getDbConnection().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("student_class_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("section")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public static ObservableList<Student> getAssignedStudents() {
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "    student_class.student_class_id, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade " +
                "FROM  student_class " +
                "JOIN Classes ON student_class.class_id = Classes.class_id "+
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Users ON students.user_id = Users.user_id " +
                "JOIN Grades ON Classes.grade_id = Grades.grade_id ";
        ;
        ObservableList<Student> students = FXCollections.observableArrayList();

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getString("student_class_id"),
                            rs.getString("full_name"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("section"),
                            rs.getString("student_class_id") // try to remove this
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }



    public static ObservableList<Student> searchAssignedStudentsByName(String searchText, String grade, String section, String academicYear) {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "    student_class.student_class_id, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade " +
                "FROM student_class " +
                "JOIN Classes ON student_class.class_id = Classes.class_id " +
                "JOIN Grades ON classes.grade_id = Grades.grade_id " +
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Users ON students.user_id = Users.user_id " +
                " WHERE full_name LIKE ? AND grade LIKE ? AND section LIKE ? AND academic_year LIKE ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, grade != null ? grade : "%");
            stmt.setString(3, section != null ? section : "%");
            stmt.setString(4, academicYear != null ? academicYear : "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getString("student_class_id"),
                            rs.getString("full_name"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("section"),
                            rs.getString("student_class_id") // try to remove this
                            );
                    students.add(student);
                }
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
                "    FROM Users " +
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

        public static void addStudent(String fullName, String email, String username, String password, String phone, String guardian) {
            String userQuery = "INSERT INTO Users (full_name, username, password, role) VALUES (?, ?, ?, 'STUDENT')";
            String studentQuery = "INSERT INTO Students (user_id, phone, guardian, email) VALUES (?, ?, ?, ?)";

            try (Connection conn = Model.getInstance().getDbConnection().getConnection();
                 PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement studentStmt = conn.prepareStatement(studentQuery)) {

                // Set the parameters for the Users table
                userStmt.setString(1, fullName);
                userStmt.setString(2, username);
                userStmt.setString(3, password);

                // Execute the Users insert query
                int rowsAffected = userStmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                // Retrieve the generated user_id from the Users table
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Now, set the parameters for the Students table using the retrieved user_id
                        studentStmt.setInt(1, userId);
                        studentStmt.setString(2, phone);
                        studentStmt.setString(3, guardian);
                        studentStmt.setString(4, email);

                        // Execute the Students insert query
                        studentStmt.executeUpdate();
                    } else {
                        throw new SQLException("User ID retrieval failed, no ID obtained.");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public static ObservableList<Student > searchStudentsByName(String searchText) {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = " SELECT" +
                " Users.full_name, " +
                " Students.student_id, " +
                " Students.guardian, " +
                " Students.phone, " +
                " Students.email " +
                " FROM Users " +
                " JOIN Students ON Users.user_id = Students.user_id " +
                "WHERE full_name LIKE ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getString("student_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("guardian")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public static void updateStudent(String studentId, String email, String phone, String guardian) {
        String query = "UPDATE students SET guardian = ?, phone = ?, email = ? WHERE student_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, guardian);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, studentId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(String studentId) {
        String query = "DELETE FROM students WHERE student_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, studentId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkAssignedStudentExist(String assignedStudentId) {
        String query = "SELECT DISTINCT student_id FROM student_class WHERE student_class_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, assignedStudentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean checkAssignedStudentExist(String classId, String studentId) {
        String query = "SELECT DISTINCT * FROM student_class WHERE student_class_id = ? AND class_id = ? AND student_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(2, classId);
            stmt.setString(3, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void updateAssignedStudent(String assignedStudentId, String classId) {
        String query = "UPDATE student_class SET class_id = ? WHERE student_class_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, classId);
            stmt.setString(2, assignedStudentId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignStudentToClass(String studentId, String classId) {
        String query = "INSERT INTO student_class(student_id,class_id) " +
                "VALUES (?,?)";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, classId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void unassignStudent(String assignId) {
        String query = "DELETE FROM student_class WHERE student_class_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, assignId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void assignResultsToStudent(String studentId, String classId)  {
        // Step 1: Get the grade_id for the class
        String gradeQuery = "SELECT grade_id FROM Classes WHERE class_id = ?";
        int gradeId = -1;

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(gradeQuery)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gradeId = rs.getInt("grade_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Step 2: Get all grade_course_ids for that grade
        String courseQuery = "SELECT grade_course_id FROM grade_course WHERE grade_id = ?";
        List<Integer> gradeCourseIds = new ArrayList<>();

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(courseQuery)) {
            ps.setInt(1, gradeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gradeCourseIds.add(rs.getInt("grade_course_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Step 3: Insert into Results table
        String insertQuery = """
        INSERT INTO Results (student_id, grade_course_id, class_id, semester)
        SELECT ?, ?, ?, ?
        WHERE NOT EXISTS (
            SELECT 1 FROM Results 
            WHERE student_id = ? AND grade_course_id = ? AND class_id = ? AND semester = ?
        )
    """;

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(insertQuery)) {
            // setup 1st semester fields
            for (int gradeCourseId : gradeCourseIds) {
                ps.setString(1, studentId);
                ps.setInt(2, gradeCourseId);
                ps.setString(3, classId);
                ps.setString(4, "1");

                ps.setString(5, studentId);
                ps.setInt(6, gradeCourseId);
                ps.setString(7, classId);
                ps.setString(8, "1");

                ps.executeUpdate();
            }

            // setup 2nd semester fields
            for (int gradeCourseId : gradeCourseIds) {
                ps.setString(1, studentId);
                ps.setInt(2, gradeCourseId);
                ps.setString(3, classId);
                ps.setString(4, "2");

                ps.setString(5, studentId);
                ps.setInt(6, gradeCourseId);
                ps.setString(7, classId);
                ps.setString(8, "2");

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void removeStudentResultsFromClass(String studentId, String classId)  {
        String deleteQuery = "DELETE FROM Results WHERE student_id = ? AND class_id = ?";

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(deleteQuery)) {
            ps.setString(1, studentId);
            ps.setString(2, classId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " result(s) for student " + studentId + " in class " + classId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public static String getStudentIdByAssignedId(String assignedStudentId) {
            String studentId = null;
            String query = "SELECT student_id FROM student_class WHERE student_class_id = ?";

            try (Connection conn = Model.getInstance().getDbConnection().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, assignedStudentId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    studentId = rs.getString("student_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return studentId;
        }

}

