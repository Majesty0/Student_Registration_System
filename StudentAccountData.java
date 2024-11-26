
package com.mycompany.studentregistrationsystem;

public class StudentAccountData {
    private int studentId;
    private double balance;
    
    public StudentAccountData(int studentId, double balance) {
        this.studentId = studentId;
        this.balance = balance;
    }
    
    public int getStudentId() {return studentId; }
    public double getBalance() {return balance; }
}
