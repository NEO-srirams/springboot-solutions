package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.AppointmentRequestDTO;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Optional<Appointment> updateAppointment(Long id, AppointmentRequestDTO request) {
        Optional<Appointment> existing = appointmentRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Appointment appointment = existing.get();
        appointment.setPatientName(request.getPatientName());
        appointment.setDoctor(request.getDoctor());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setFee(request.getFee());
        appointment.setStatus(request.getStatus());
        return Optional.of(appointmentRepository.save(appointment));
    }

    @Override
    public boolean deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            return false;
        }
        appointmentRepository.deleteById(id);
        return true;
    }
}
