package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Course;

public interface CourseService {

    Course saveCourse(Course course);

    Optional<Course> getCourseById(Long id);
}
