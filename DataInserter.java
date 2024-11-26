package com.mycompany.studentregistrationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInserter {
    private static final Logger LOGGER = Logger.getLogger(DataInserter.class.getName());

    public static void insertStudentData(StudentData student) {
        String sql = "INSERT INTO StudentRegistration "
                   + "(title, last_name, first_name, middle_name, street_address, city, postal_code, cell_phone, po_box, "
                   + "county, country, email, dob, gender, place_of_birth, citizenship, passport_number, marital_status, "
                   + "kin_name, kin_address, kin_town, kin_phone, kin_email, semester, enrollment_year, degree, "
                   + "sponsor_name, sponsor_address, sponsor_phone, sponsor_email, disability, referral_source, "
                   + "signature, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getTitle());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getFirstName());
            pstmt.setString(4, student.getMiddleName());
            pstmt.setString(5, student.getStreetAddress());
            pstmt.setString(6, student.getCity());
            pstmt.setString(7, student.getPostalCode());
            pstmt.setString(8, student.getCellPhone());
            pstmt.setString(9, student.getPoBox());
            pstmt.setString(10, student.getCounty());
            pstmt.setString(11, student.getCountry());
            pstmt.setString(12, student.getEmail());
            pstmt.setString(13, student.getDob());
            pstmt.setString(14, student.getGender());
            pstmt.setString(15, student.getPlaceOfBirth());
            pstmt.setString(16, student.getCitizenship());
            pstmt.setString(17, student.getPassportNumber());
            pstmt.setString(18, student.getMaritalStatus());
            pstmt.setString(19, student.getKinName());
            pstmt.setString(20, student.getKinAddress());
            pstmt.setString(21, student.getKinTown());
            pstmt.setString(22, student.getKinPhone());
            pstmt.setString(23, student.getKinEmail());
            pstmt.setString(24, student.getSemester());
            pstmt.setInt(25, student.getEnrollmentYear());
            pstmt.setString(26, student.getDegree());
            pstmt.setString(27, student.getSponsorName());
            pstmt.setString(28, student.getSponsorAddress());
            pstmt.setString(29, student.getSponsorPhone());
            pstmt.setString(30, student.getSponsorEmail());
            pstmt.setString(31, student.getDisability());
            pstmt.setString(32, student.getReferralSource());
            pstmt.setString(33, student.getSignature());
            pstmt.setString(34, student.getDate());

            pstmt.executeUpdate();
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    insertStudentAccountData(new StudentAccountData(generatedId, 0));
                }
            }
            
            LOGGER.info("Data inserted successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting data: " + e.getMessage(), e);
        }
    }
    
    public static void insertTransactionData(TransactionData transaction) {
        String query = "SELECT COUNT(*) FROM StudentAccounts WHERE student_id = ?";
        String insertQuery = "INSERT INTO Transactions (student_id, amount_paid) VALUES (?, ?)";
        String updateQuery = "UPDATE StudentAccounts SET balance=balance - ? WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
             PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
            
            pstmt.setInt(1, transaction.getStudentId());
            
            insertPstmt.setInt(1, transaction.getStudentId());
            insertPstmt.setDouble(2, transaction.getAmountPaid());
            
            updatePstmt.setDouble(1, transaction.getAmountPaid());
            updatePstmt.setInt(2, transaction.getStudentId());
            
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        insertPstmt.executeUpdate();
                        updatePstmt.executeUpdate();
                        LOGGER.info("Transaction completed successfully");
                    } else {
                        System.out.println("You do not have a student account");
                    }
                }
            } 
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting data: " + e.getMessage(), e);
        }
    }
    
    
    public static void insertStudentCourseRegistrationData(StudentCourseRegistrationData studentCourseRegistration) {
       String updateBalanceQuery = "UPDATE StudentAccounts SET balance = balance + ? WHERE student_id = ?";
       String updateCourseRegistrationQuery = "UPDATE StudentCourses SET number_of_courses = ? WHERE student_id = ?";
       String insertCourseRegistrationQuery = "INSERT INTO StudentCourses (student_id, number_of_courses, semester) VALUES (?, ?, ?)";
       String checkingStudentQuery = "SELECT degree FROM StudentRegistration WHERE student_id = ?";
       String checkingCourseRegistrationQuery = "SELECT * FROM StudentCourses WHERE student_id = ? AND semester = ?";

       try (Connection conn = DatabaseConnection.getConnection()) {
           conn.setAutoCommit(false); // Start transaction

           try (PreparedStatement updateBalancePstmt = conn.prepareStatement(updateBalanceQuery);
                PreparedStatement updateCourseRegistrationPstmt = conn.prepareStatement(updateCourseRegistrationQuery);
                PreparedStatement insertCourseRegistrationPstmt = conn.prepareStatement(insertCourseRegistrationQuery);
                PreparedStatement checkingStudentPstmt = conn.prepareStatement(checkingStudentQuery);
                PreparedStatement checkingCourseRegistrationPstmt = conn.prepareStatement(checkingCourseRegistrationQuery)) {

               checkingStudentPstmt.setInt(1, studentCourseRegistration.getStudentId());
               checkingCourseRegistrationPstmt.setInt(1, studentCourseRegistration.getStudentId());
               checkingCourseRegistrationPstmt.setString(2, studentCourseRegistration.getSemester());

               try (ResultSet resultSet = checkingStudentPstmt.executeQuery()) {
                   if (resultSet.next()) {
                       String degree = resultSet.getString("degree");
                       boolean courseExists;
                       int existingCourses = 0;

                       try (ResultSet courseRegistration = checkingCourseRegistrationPstmt.executeQuery()) {
                           courseExists = courseRegistration.next();
                           if (courseExists) {
                               existingCourses = courseRegistration.getInt("number_of_courses");
                           }
                       }

                       int num_of_courses = courseExists
                           ? studentCourseRegistration.getNumOfCourses() - existingCourses
                           : studentCourseRegistration.getNumOfCourses();

                       if (num_of_courses < 0) {
                           LOGGER.warning("Invalid number of courses.");
                           conn.rollback(); // Rollback transaction
                           return;
                       }

                       double balance = calculateBalance(degree, num_of_courses);

                       if (courseExists) {
                           updateCourseRegistrationPstmt.setInt(1, num_of_courses);
                           updateCourseRegistrationPstmt.setInt(2, studentCourseRegistration.getStudentId());
                           updateCourseRegistrationPstmt.executeUpdate();
                           LOGGER.info("Course Registration updated successfully");
                       } else {
                           insertCourseRegistrationPstmt.setInt(1, num_of_courses);
                           insertCourseRegistrationPstmt.setInt(2, studentCourseRegistration.getStudentId());
                           insertCourseRegistrationPstmt.executeUpdate();
                           LOGGER.info("Course Registration inserted successfully");
                       }

                       updateBalancePstmt.setDouble(1, balance);
                       updateBalancePstmt.setInt(2, studentCourseRegistration.getStudentId());
                       updateBalancePstmt.executeUpdate();
                   } else {
                       LOGGER.info("Student not found in registration records.");
                   }
               }

               conn.commit(); // Commit transaction
           } catch (SQLException e) {
               conn.rollback(); // Rollback on failure
               LOGGER.log(Level.SEVERE, "Error processing registration: " + e.getMessage(), e);
           }
       } catch (SQLException e) {
           LOGGER.log(Level.SEVERE, "Database connection error: " + e.getMessage(), e);
       }
   }
    
    public static double calculateBalance(String degree, int num_of_courses) {
        if (degree == null || degree.isEmpty()) {
            throw new IllegalArgumentException("Degree cannot be null or empty");
        }

        if (num_of_courses < 0) {
            throw new IllegalArgumentException("Number of courses cannot be negative");
        }

        Map<String, Double> degreeCosts = Map.of(
            "Bachelor's", 2000.0,
            "Master's", 4000.0,
            "Doctoral", 5500.0,
            "Non-degree seeking", 1500.0
        );

        if (!degreeCosts.containsKey(degree)) {
            throw new IllegalArgumentException("Unknown degree type: " + degree);
        }

        double cost = degreeCosts.get(degree);
        return cost * num_of_courses;
    }

    
    public static void insertStudentAccountData(StudentAccountData studentAccount) {
        String query  = "INSERT INTO StudentAccounts (student_id, balance) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, studentAccount.getStudentId());
            pstmt.setDouble(2, studentAccount.getBalance());
            
            pstmt.executeUpdate();
            LOGGER.info("Student account Created successfully");
            
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting data: " + e.getMessage(), e);

        }
    }
}
