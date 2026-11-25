package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.EventRequestDTO;
import com.examly.springapp.model.Event;
import com.examly.springapp.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Optional<Event> updateEvent(Long id, EventRequestDTO request) {
        Optional<Event> existing = eventRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Event event = existing.get();
        event.setTitle(request.getTitle());
        event.setVenue(request.getVenue());
        event.setEventDate(request.getEventDate());
        event.setCapacity(request.getCapacity());
        event.setStatus(request.getStatus());
        return Optional.of(eventRepository.save(event));
    }

    @Override
    public boolean deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            return false;
        }
        eventRepository.deleteById(id);
        return true;
    }
}
