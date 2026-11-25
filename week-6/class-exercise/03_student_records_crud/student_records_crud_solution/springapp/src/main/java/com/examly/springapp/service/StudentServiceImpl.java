package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.StudentRequestDTO;
import com.examly.springapp.model.Student;
import com.examly.springapp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> updateStudent(Long id, StudentRequestDTO request) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Student student = existing.get();
        student.setName(request.getName());
        student.setDepartment(request.getDepartment());
        student.setYear(request.getYear());
        student.setCgpa(request.getCgpa());
        return Optional.of(studentRepository.save(student));
    }

    @Override
    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
