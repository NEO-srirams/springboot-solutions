package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Lesson;
import com.examly.springapp.model.Course;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);

    List<Lesson> getAllLessons();

    Optional<Lesson> getLessonById(Long id);

    List<Lesson> getLessonsByCourseId(Long courseId);

    Optional<Lesson> createLessonForCourse(Long courseId, Lesson lesson);
}
