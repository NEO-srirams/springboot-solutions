package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.IdCard;
import com.examly.springapp.model.Student;
import com.examly.springapp.repository.StudentRepository;
import com.examly.springapp.repository.IdCardRepository;

@Service
public class IdCardServiceImpl implements IdCardService {

    private final IdCardRepository idCardRepository;
    private final StudentRepository studentRepository;

    public IdCardServiceImpl(IdCardRepository idCardRepository, StudentRepository studentRepository) {
        this.idCardRepository = idCardRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public IdCard saveIdCard(IdCard idCard) {
        return idCardRepository.save(idCard);
    }

    @Override
    public List<IdCard> getAllIdCards() {
        return idCardRepository.findAll();
    }

    @Override
    public Optional<IdCard> getIdCardById(Long id) {
        return idCardRepository.findById(id);
    }

    @Override
    public Optional<IdCard> getIdCardByStudentId(Long studentId) {
        return idCardRepository.findByStudentId(studentId);
    }

    @Override
    public boolean studentHasIdCard(Long studentId) {
        return idCardRepository.existsByStudentId(studentId);
    }

    @Override
    public Optional<IdCard> createIdCardForStudent(Long studentId, IdCard idCard) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            return Optional.empty();
        }
        idCard.setId(null);
        student.get().setIdCard(idCard);
        return Optional.of(idCardRepository.save(idCard));
    }
}
