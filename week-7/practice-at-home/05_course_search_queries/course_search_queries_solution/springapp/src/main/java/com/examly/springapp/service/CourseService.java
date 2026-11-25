package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Course;

public interface CourseService {

    List<Course> getCourseByLevel(String level);

    List<Course> searchCourse(String keyword);

    List<Course> getCourseByFeeRange(Double min, Double max);

    List<Course> filterCourse(String level, Double min);

    List<Course> eitherCourse(String level, String keyword);

    List<Course> getCourseAboveFee(Double min);

    List<Course> getCourseUpToFee(Double max);

    int updateStatusByLevel(String level, String status);

    Page<Course> getCoursePage(int page, int size, String sortBy, String direction);
}
