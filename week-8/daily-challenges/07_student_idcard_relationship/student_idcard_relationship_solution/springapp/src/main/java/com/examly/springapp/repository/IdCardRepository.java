package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.IdCard;

public interface IdCardRepository extends JpaRepository<IdCard, Long> {

    Optional<IdCard> findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);
}
