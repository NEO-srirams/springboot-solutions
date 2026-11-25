package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Course;
import com.examly.springapp.model.Student;
import com.examly.springapp.repository.CourseRepository;
import com.examly.springapp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAllWithCourses();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean hasCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        return student.getCourses().stream().anyMatch(x -> x.getId().equals(courseId));
    }

    @Transactional
    @Override
    public Student addCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        student.addCourse(course);
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student removeCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        student.removeCourse(course);
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
