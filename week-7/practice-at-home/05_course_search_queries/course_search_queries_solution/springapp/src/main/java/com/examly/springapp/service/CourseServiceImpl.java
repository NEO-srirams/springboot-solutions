package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<Course> getCourseByLevel(String level) {
        return courseRepository.findByLevelIgnoreCaseOrderByFeeDesc(level);
    }

    @Override
    public List<Course> searchCourse(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Course> getCourseByFeeRange(Double min, Double max) {
        return courseRepository.findByFeeBetween(min, max);
    }

    @Override
    public List<Course> filterCourse(String level, Double min) {
        return courseRepository.findByLevelIgnoreCaseAndFeeGreaterThanEqual(level, min);
    }

    @Override
    public List<Course> eitherCourse(String level, String keyword) {
        return courseRepository.findByLevelIgnoreCaseOrTitleContainingIgnoreCase(level, keyword);
    }

    @Override
    public List<Course> getCourseAboveFee(Double min) {
        return courseRepository.findAboveFee(min);
    }

    @Override
    public List<Course> getCourseUpToFee(Double max) {
        return courseRepository.findUpToFeeNative(max);
    }

    @Override
    public int updateStatusByLevel(String level, String status) {
        return courseRepository.updateStatusByLevel(level, status);
    }

    @Override
    public Page<Course> getCoursePage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return courseRepository.findAll(PageRequest.of(page, size, sort));
    }
}
