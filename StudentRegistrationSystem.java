package com.mycompany.studentregistrationsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StudentRegistrationSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Registration System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Registration Form Tab
            JPanel registrationPanel = new JPanel();
            registrationPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Dynamic layout
            addRegistrationForm(registrationPanel);
            tabbedPane.addTab("Register Student", new JScrollPane(registrationPanel));

            // View Registered Students Tab
            JPanel viewPanel = new JPanel(new BorderLayout());
            addStudentTable(viewPanel);
            tabbedPane.addTab("View Registered Students", viewPanel);
            
            // Course Registration Tab
            JPanel courseRegistrationPanel = new JPanel();
            courseRegistrationPanel.setLayout(new GridLayout(0, 2, 5, 5));
            addCourseRegistrationForm(courseRegistrationPanel);
            tabbedPane.addTab("Course Registration", courseRegistrationPanel);
            
            // Transactions Tab
            JPanel transactionPanel = new JPanel(new BorderLayout());
            transactionPanel.setLayout(new GridLayout(0, 2, 5, 5));
            addTransactionsForm(transactionPanel);
            tabbedPane.addTab("Make Transaction", transactionPanel);
            

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }

    private static void addRegistrationForm(JPanel panel) {
        // Section: Personal Information
        addSectionHeading(panel, "Personal Information");
        JComboBox<String> titleField = createComboBox(panel, "Title", new String[]{"Mr.", "Ms.", "Mrs."});
        JTextField lastNameField = createField(panel, "Last Name");
        JTextField firstNameField = createField(panel, "First Name");
        JTextField middleNameField = createField(panel, "Middle Name");
        JTextField streetAddressField = createField(panel, "Street Address");
        JTextField cityField = createField(panel, "City");
        JTextField postalCodeField = createField(panel, "Postal Code");
        JTextField cellPhoneField = createField(panel, "Cell Phone");
        JTextField poBoxField = createField(panel, "P.O. Box");
        JTextField countyField = createField(panel, "County");
        JTextField countryField = createField(panel, "Country");
        JTextField emailField = createField(panel, "Email");
        JTextField dobField = createField(panel, "Date of Birth");
        JComboBox<String> genderField = createComboBox(panel, "Gender", new String[]{"Male", "Female"});
        JTextField placeOfBirthField = createField(panel, "Place of Birth");
        JTextField citizenshipField = createField(panel, "Citizenship");
        JTextField passportNumberField = createField(panel, "Passport Number");
        JComboBox<String> maritalStatusField = createComboBox(panel, "Marital Status", new String[]{"Single", "Married"});

        // Section: Next of Kin Information
        addSectionHeading(panel, "Next of Kin Information");
        JTextField kinNameField = createField(panel, "Next of Kin Name");
        JTextField kinAddressField = createField(panel, "Next of Kin Address");
        JTextField kinTownField = createField(panel, "Next of Kin Town");
        JTextField kinPhoneField = createField(panel, "Next of Kin Phone");
        JTextField kinEmailField = createField(panel, "Next of Kin Email");

        // Section: Academic Information
        addSectionHeading(panel, "Academic Information");
        JComboBox<String> semesterField = createComboBox(panel, "Semester", new String[]{"Fall", "Spring", "Summer"});
        JTextField enrollmentYearField = createField(panel, "Enrollment Year");
        JComboBox<String> degreeField = createComboBox(panel, "Degree", new String[]{"Bachelor's", "Master's", "Doctoral", "Non-degree seeking"});

        // Section: Sponsor Information
        addSectionHeading(panel, "Sponsor Information");
        JTextField sponsorNameField = createField(panel, "Sponsor Name");
        JTextField sponsorAddressField = createField(panel, "Sponsor Address");
        JTextField sponsorPhoneField = createField(panel, "Sponsor Phone");
        JTextField sponsorEmailField = createField(panel, "Sponsor Email");

        // Section: Other Information
        addSectionHeading(panel, "Other Information");
        JComboBox<String> disabilityField = createComboBox(panel, "Disability", new String[]{"None", "Hearing", "Mobility", "Sight", "Learning disability"});
        JComboBox<String> referralSourceField = createComboBox(panel, "Referral Source", new String[]{"Newspaper", "TV", "Radio", "College Fair", "Exhibition", "Social Media"});
        JTextField signatureField = createField(panel, "Signature");
        JTextField dateField = createField(panel, "Date");

        // Submit Button
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                StudentData student = new StudentData(
                        (String) titleField.getSelectedItem(), lastNameField.getText(), firstNameField.getText(),
                        middleNameField.getText(), streetAddressField.getText(), cityField.getText(),
                        postalCodeField.getText(), cellPhoneField.getText(), poBoxField.getText(),
                        countyField.getText(), countryField.getText(), emailField.getText(),
                        dobField.getText(), (String) genderField.getSelectedItem(), placeOfBirthField.getText(),
                        citizenshipField.getText(), passportNumberField.getText(), (String) maritalStatusField.getSelectedItem(),
                        kinNameField.getText(), kinAddressField.getText(), kinTownField.getText(),
                        kinPhoneField.getText(), kinEmailField.getText(), (String) semesterField.getSelectedItem(),
                        Integer.parseInt(enrollmentYearField.getText()), (String) degreeField.getSelectedItem(),
                        sponsorNameField.getText(), sponsorAddressField.getText(), sponsorPhoneField.getText(),
                        sponsorEmailField.getText(), (String) disabilityField.getSelectedItem(), (String) referralSourceField.getSelectedItem(),
                        signatureField.getText(), dateField.getText()
                );

                DataInserter.insertStudentData(student);
                
                
                
                JOptionPane.showMessageDialog(panel, "Registration submitted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Submission Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void addStudentTable(JPanel panel) {
        JTable studentTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        studentTable.setModel(tableModel);

        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        panel.add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            try {
                ResultSet rs = DataRetriever.retrieveStudentData();
                tableModel.setRowCount(0);
                tableModel.setColumnIdentifiers(new String[]{
                        "ID", "Last Name", "First Name", "Degree", "Balance"
                });

                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add(rs.getString("student_id"));
                    row.add(rs.getString("last_name"));
                    row.add(rs.getString("first_name"));
                    row.add(rs.getString("degree"));
                    row.add(rs.getString("balance"));
                    tableModel.addRow(row);
                }

                rs.getStatement().getConnection().close(); // Close connection after use
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Error loading data: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private static void addTransactionsForm(JPanel panel) {
        addSectionHeading(panel, "Transactions Form");
        JTextField studentIdField = createField(panel, "Student ID");
        JTextField amountPaidField = createField(panel, "Amount paid");
        
        // Set maximum size to prevent stretching
        
        JButton submitButton = new JButton("Make Transaction");
        panel.add(submitButton);
        
        submitButton.addActionListener(e -> {
            try {
                TransactionData transaction = new TransactionData(
                        Integer.parseInt(studentIdField.getText()),
                        Double.parseDouble(amountPaidField.getText())
                );
                
                DataInserter.insertTransactionData(transaction);
                JOptionPane.showMessageDialog(panel, "Transaction made successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "You do not have a student account");
            }
        });
    }
    
    private static void addCourseRegistrationForm(JPanel panel) {
        addSectionHeading(panel, "Course Registration");
        JTextField studentIdField = createField(panel, "Student ID");
        JComboBox<String> semesterField = createComboBox(panel, "Semester", new String[]{"Fall", "Spring", "Summer"});
        JTextField numOfCoursesField = createField(panel, "Number of Courses");
        
        JButton submitButton = new JButton("Register For Courses");
        panel.add(submitButton);
        
        submitButton.addActionListener(e -> {
           try {
               StudentCourseRegistrationData studentCourseRegistration = new StudentCourseRegistrationData(
                       Integer.parseInt(studentIdField.getText()),
                       Integer.parseInt(numOfCoursesField.getText()),
                       (String) semesterField.getSelectedItem()
               );
               
               DataInserter.insertStudentCourseRegistrationData(studentCourseRegistration);
               JOptionPane.showMessageDialog(panel, "Course Registration updated successfully");
           } catch (Exception ex) {
              JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Submission Error", JOptionPane.ERROR_MESSAGE);
           }
        });
    }

    private static JTextField createField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        panel.add(jLabel);
        panel.add(textField);
        return textField;
    }

    private static JComboBox<String> createComboBox(JPanel panel, String label, String[] options) {
        JLabel jLabel = new JLabel(label);
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(jLabel);
        panel.add(comboBox);
        return comboBox;
    }

    private static void addSectionHeading(JPanel panel, String headingText) {
        JLabel headingLabel = new JLabel(headingText);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headingLabel);
        panel.add(new JLabel()); // Empty cell for layout alignment
    }
}
