
package com.mycompany.studentregistrationsystem;

public class StudentCourseRegistrationData {
    private int studentId;
    private int numOfCourses;
    private String semester;
    
    public StudentCourseRegistrationData(int studentId, int numOfCourses, String semester) {
        this.studentId = studentId;
        this.numOfCourses = numOfCourses;
        this.semester = semester;
    }
    
    public int getStudentId() {return studentId;}
    public int getNumOfCourses() {return numOfCourses;}
    public String getSemester() {return semester;}
}
