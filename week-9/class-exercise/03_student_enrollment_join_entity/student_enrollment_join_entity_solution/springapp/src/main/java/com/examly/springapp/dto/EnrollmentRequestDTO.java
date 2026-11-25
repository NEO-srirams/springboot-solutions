package com.examly.springapp.dto;

import java.time.LocalDate;

public class EnrollmentRequestDTO {

    private Long studentId;
    private Long courseId;
    private LocalDate enrolledOn;
    private String grade;

    public EnrollmentRequestDTO() {
    }

    public EnrollmentRequestDTO(Long studentId, Long courseId, LocalDate enrolledOn, String grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledOn = enrolledOn;
        this.grade = grade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
