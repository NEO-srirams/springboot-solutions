package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Drive;

public interface DriveRepository extends JpaRepository<Drive, Long> {
}
