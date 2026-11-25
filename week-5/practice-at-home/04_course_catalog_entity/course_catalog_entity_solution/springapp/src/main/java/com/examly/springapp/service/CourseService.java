package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Course;

public interface CourseService {

    Course saveCourse(Course course);

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);
}
