package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Student;
import com.examly.springapp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudentByDepartment(String department) {
        return studentRepository.findByDepartmentIgnoreCaseOrderByMarksDesc(department);
    }

    @Override
    public List<Student> searchStudent(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Student> getStudentByMarksRange(Integer min, Integer max) {
        return studentRepository.findByMarksBetween(min, max);
    }

    @Override
    public List<Student> filterStudent(String department, Integer min) {
        return studentRepository.findByDepartmentIgnoreCaseAndMarksGreaterThanEqual(department, min);
    }

    @Override
    public List<Student> eitherStudent(String department, String keyword) {
        return studentRepository.findByDepartmentIgnoreCaseOrNameContainingIgnoreCase(department, keyword);
    }

    @Override
    public List<Student> getStudentAboveMarks(Integer min) {
        return studentRepository.findAboveMarks(min);
    }

    @Override
    public List<Student> getStudentUpToMarks(Integer max) {
        return studentRepository.findUpToMarksNative(max);
    }

    @Override
    public int updateStatusByDepartment(String department, String status) {
        return studentRepository.updateStatusByDepartment(department, status);
    }

    @Override
    public Page<Student> getStudentPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return studentRepository.findAll(PageRequest.of(page, size, sort));
    }
}
