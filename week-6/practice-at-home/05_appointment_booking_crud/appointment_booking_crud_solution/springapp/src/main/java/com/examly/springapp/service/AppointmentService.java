package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.AppointmentRequestDTO;
import com.examly.springapp.model.Appointment;

public interface AppointmentService {

    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    Optional<Appointment> updateAppointment(Long id, AppointmentRequestDTO request);

    boolean deleteAppointment(Long id);
}
