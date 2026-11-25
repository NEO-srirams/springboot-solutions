package com.examly.springapp.dto;

import java.time.LocalDate;

public class EnrollmentResponseDTO {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private LocalDate enrolledOn;
    private String grade;

    public EnrollmentResponseDTO() {
    }

    public EnrollmentResponseDTO(Long id, Long studentId, String studentName, Long courseId, String courseName, LocalDate enrolledOn, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrolledOn = enrolledOn;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getEnrolledOn() {
        return enrolledOn;
    }

    public void setEnrolledOn(LocalDate enrolledOn) {
        this.enrolledOn = enrolledOn;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
