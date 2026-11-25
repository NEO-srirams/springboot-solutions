package com.examly.springapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Course;
import com.examly.springapp.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}
