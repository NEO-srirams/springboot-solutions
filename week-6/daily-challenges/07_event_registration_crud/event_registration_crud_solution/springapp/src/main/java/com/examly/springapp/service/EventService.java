package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.EventRequestDTO;
import com.examly.springapp.model.Event;

public interface EventService {

    Event saveEvent(Event event);

    List<Event> getAllEvents();

    Optional<Event> getEventById(Long id);

    Optional<Event> updateEvent(Long id, EventRequestDTO request);

    boolean deleteEvent(Long id);
}
