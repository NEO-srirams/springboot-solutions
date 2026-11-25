package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Question;

public interface QuestionService {

    Question saveQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);
}
