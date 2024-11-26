package com.mycompany.studentregistrationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataRetriever {
    private static final Logger LOGGER = Logger.getLogger(DataRetriever.class.getName());

    /**
     * Retrieves student registration data from the database.
     *
     * @return ResultSet containing the query results
     * @throws SQLException if a database access error occurs
     */
    public static ResultSet retrieveStudentData() throws SQLException {
        String sql = "SELECT a.student_id, a.first_name, a.last_name, a.degree, b.balance FROM StudentRegistration as a JOIN StudentAccounts as b on a.student_id=b.student_id";

        // Use a connection from the DatabaseConnection class
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection failed.");
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery(); // Caller will handle the ResultSet and resource closure
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving data.", e);
            throw e;
        }
    }
    
    
    
    
}
