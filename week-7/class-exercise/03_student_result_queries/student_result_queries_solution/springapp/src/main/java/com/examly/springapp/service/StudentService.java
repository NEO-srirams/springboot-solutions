package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Student;

public interface StudentService {

    List<Student> getStudentByDepartment(String department);

    List<Student> searchStudent(String keyword);

    List<Student> getStudentByMarksRange(Integer min, Integer max);

    List<Student> filterStudent(String department, Integer min);

    List<Student> eitherStudent(String department, String keyword);

    List<Student> getStudentAboveMarks(Integer min);

    List<Student> getStudentUpToMarks(Integer max);

    int updateStatusByDepartment(String department, String status);

    Page<Student> getStudentPage(int page, int size, String sortBy, String direction);
}
