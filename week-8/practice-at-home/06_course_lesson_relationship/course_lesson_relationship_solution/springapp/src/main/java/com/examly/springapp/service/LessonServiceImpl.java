package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Lesson;
import com.examly.springapp.model.Course;
import com.examly.springapp.repository.CourseRepository;
import com.examly.springapp.repository.LessonRepository;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    @Override
    public Optional<Lesson> createLessonForCourse(Long courseId, Lesson lesson) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            return Optional.empty();
        }
        lesson.setId(null);
        course.get().addLesson(lesson);
        return Optional.of(lessonRepository.save(lesson));
    }
}
