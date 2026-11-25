package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.CourseRequestDTO;
import com.examly.springapp.model.Course;

public interface CourseService {

    Course saveCourse(Course course);

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    Optional<Course> updateCourse(Long id, CourseRequestDTO request);

    boolean deleteCourse(Long id);
}
