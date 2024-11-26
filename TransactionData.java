
package com.mycompany.studentregistrationsystem;

public class TransactionData {
    private int studentId;
    private double amountPaid;
    
    public TransactionData(int studentId, double amountPaid) {
        this.studentId = studentId;
        this.amountPaid = amountPaid;
    }
    
    public int getStudentId() {return studentId;}
    public double getAmountPaid() {return amountPaid; }
}

