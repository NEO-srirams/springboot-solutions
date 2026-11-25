package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.IdCard;
import com.examly.springapp.model.Student;

public interface IdCardService {

    IdCard saveIdCard(IdCard idCard);

    List<IdCard> getAllIdCards();

    Optional<IdCard> getIdCardById(Long id);

    Optional<IdCard> getIdCardByStudentId(Long studentId);

    boolean studentHasIdCard(Long studentId);

    Optional<IdCard> createIdCardForStudent(Long studentId, IdCard idCard);
}
