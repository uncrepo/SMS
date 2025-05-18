package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentDAO {


    public static void setAlreadyAssigned(String studentId) {
            String query = "  UPDATE last_year_record " +
                    "JOIN student_class ON last_year_record.student_class_id = student_class.student_class_id "+
                    "JOIN Students ON student_class.student_id = Students.student_id " +
                    "JOIN Classes ON student_class.class_id = classes.class_id " +
                    "SET last_year_record.assigned_next_grade = ? " +
                    "WHERE students.student_id = ? AND last_year_record.assigned_next_grade = 'NO' AND classes.academic_year = '2021/22' ";
            ;
            ObservableList<NotAssignedStudent> students = FXCollections.observableArrayList();

            try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

                stmt.setString(1,"YES");
                stmt.setString(2,studentId);
                stmt.executeUpdate();

                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

    public static void setNotAssigned(String studentId) {
        String query = "  UPDATE last_year_record " +
                "JOIN student_class ON last_year_record.student_class_id = student_class.student_class_id "+
                "JOIN Students ON student_class.student_id = Students.student_id " +
                "JOIN Classes ON student_class.class_id = classes.class_id " +
                "SET last_year_record.assigned_next_grade = ?" +
                "WHERE students.student_id = ? AND last_year_record.assigned_next_grade = 'YES' AND classes.academic_year = '2021/22' ";
        ;
        ObservableList<NotAssignedStudent> students = FXCollections.observableArrayList();

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1,"NO");
            stmt.setString(2, studentId);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public Student getStudentByUserId(String userId) {
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "  Users.role, " +
                "  Students.student_id,"+
                "  Users.status,"+
                "  Students.gender, "+
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
                "JOIN Grades ON Classes.grade_id = Grades.grade_id where Users.user_id = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                return new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("status")
                        );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ObservableList<Student> getAllStudents() {
        ObservableList<Student> teachers = FXCollections.observableArrayList();
        String query = " SELECT " +
                "    Users.full_name, " +
                "    Students.student_id, " +
                "    Students.guardian, " +
                "    Students.phone, " +
                "    Students.email, " +
                "    Students.gender, "+
                "    Users.status "+
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
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("status")

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

    public static ObservableList<NotAssignedStudent> getStudentsNotAssigned() {
        String query = "  SELECT  " +
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
                "WHERE last_year_record.assigned_next_grade = 'NO' ";
        ;
        ObservableList<NotAssignedStudent> students = FXCollections.observableArrayList();

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NotAssignedStudent student = new NotAssignedStudent(
                            rs.getString("student_id"),
                            rs.getString("full_name"),
                            rs.getString("comment"),
                            rs.getString("grade"),
                            rs.getString("average"),
                            rs.getString("academic_year")
                    );
                    students.add(student);
                }
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

    public static List<Result> getStudentsClassResultsByGradeYearSection(String grade, String year, String section) {
        List<Result> results = new ArrayList<>();

        String query ="  SELECT  " +
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
                " AND grades.grade_id = ? AND classes.academic_year = ? AND classes.section = ? " +
                " ORDER BY average DESC ";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Result(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("average"),
                        rs.getString("comment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void addStudent(AddStudent newStudent) {
        String userQuery = "INSERT INTO Users (full_name, username, password, role) VALUES (?, ?, ?, 'STUDENT')";
        String teacherQuery = "INSERT INTO Students (user_id, phone, guardian, email, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement teacherStmt = conn.prepareStatement(teacherQuery)) {

            // Set the parameters for the Users table
            userStmt.setString(1, newStudent.getFullName());
            userStmt.setString(2, newStudent.getUsername());
            userStmt.setString(3, newStudent.getPassword());

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
                    teacherStmt.setInt(1, userId);
                    teacherStmt.setString(2, newStudent.getPhone());
                    teacherStmt.setString(3, newStudent.getGuardian());
                    teacherStmt.setString(4, newStudent.getEmail());
                    teacherStmt.setString(5, newStudent.getGender());

                    // Execute the Students insert query
                    teacherStmt.executeUpdate();
                } else {
                    throw new SQLException("User ID retrieval failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    public static ObservableList<Student > searchStudentsByName(String searchText) {
//        ObservableList<Student> students = FXCollections.observableArrayList();
//        String query = " SELECT" +
//                " Users.full_name, " +
//                " Students.student_id, " +
//                " Students.guardian, " +
//                " Students.phone, " +
//                " Students.email " +
//                " FROM Users " +
//                " JOIN Students ON Users.user_id = Students.user_id " +
//                "WHERE full_name LIKE ?";
//
//        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
//            stmt.setString(1, "%" + searchText + "%");
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    Student student = new Student(
//                            rs.getString("student_id"),
//                            rs.getString("full_name"),
//                            rs.getString("email"),
//                            rs.getString("phone"),
//                            rs.getString("guardian")
//                    );
//                    students.add(student);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return students;
//    }


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
        String query = "SELECT DISTINCT * FROM student_class WHERE class_id = ? AND student_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, classId);
            stmt.setString(2, studentId);

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

    public static ObservableList<Student> searchStudentsByName(String searchText) {
        ObservableList<Student> students= FXCollections.observableArrayList();
        String query = " SELECT" +
                " Users.full_name, " +
                " Students.student_id, " +
                " Students.guardian, " +
                " Students.phone, " +
                " Students.email, " +
                " Students.gender, " +
                " Users.status " +
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
                            rs.getString("guardian"),
                            rs.getString("phone"),
                            rs.getString("gender"),
                            rs.getString("status")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void updateStudentInfo(Student student) {
        String query = "UPDATE Students " +
                "JOIN Users ON Students.user_id = Users.user_id " +
                "SET Users.full_name = ?, " +
                "Students.email = ?, " +
                "Students.phone = ?, " +
                "Students.guardian = ?, " +
                "Students.gender = ?, " +
                "Users.status = ? " +
                "WHERE Students.student_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getGuardian());
            stmt.setString(5, student.getGender());
            stmt.setString(6, student.getStatus());

            stmt.setString(7, student.getStudentId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update teacher: " + e.getMessage());
        }
    }

    public static void updateStudent(Student student) {
        String query = "UPDATE Students s " +
                "JOIN Users u ON s.user_id = u.user_id " +
                "SET u.full_name = ?, " +
                "u.email = ?, " +
                "u.phone = ?, " +
                "u.guardian = ?, " +
                "s.grade_id = ?, " +
                "s.section = ?, " +
                "s.academic_year = ? " +
                "WHERE s.student_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getGuardian());
            stmt.setString(5, GradeDAO.getGradeID(student.getGrade()));
            stmt.setString(6, student.getSection());
            stmt.setString(7, student.getAcademicYear());
            stmt.setString(8, student.getStudentId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update student: " + e.getMessage());
        }
    }

//    public static void deleteStudent(String studentId) {
//        String query = "DELETE Students, Users FROM Students " +
//                "JOIN Users ON Students.user_id = Users.user_id " +
//                "WHERE Students.student_id = ?";
//
//        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
//            stmt.setString(1, studentId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to delete student: " + e.getMessage());
//        }
//    }

    public static List<Student> searchStudents(String searchText) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT " +
                "Users.full_name, " +
                "Students.student_id, " +
                "Users.phone, " +
                "Users.email, " +
                "Users.guardian, " +
                "Students.grade_id, " +
                "Students.section, " +
                "Students.academic_year, " +
                "Students.is_active " +
                "FROM Users " +
                "JOIN Students ON Users.user_id = Students.user_id " +
                "WHERE Users.full_name LIKE ? OR Students.student_id LIKE ? OR Users.email LIKE ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("guardian"),
                        rs.getString("phone"),
                        rs.getString("grade_id"),
                        rs.getString("section"),
                        rs.getString("academic_year")
//                        rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }



}

