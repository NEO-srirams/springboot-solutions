package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CourseRequestDTO;
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
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Optional<Course> updateCourse(Long id, CourseRequestDTO request) {
        Optional<Course> existing = courseRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Course course = existing.get();
        course.setTitle(request.getTitle());
        course.setCredits(request.getCredits());
        course.setFacultyName(request.getFacultyName());
        return Optional.of(courseRepository.save(course));
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }
        courseRepository.deleteById(id);
        return true;
    }
}
