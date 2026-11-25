package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
