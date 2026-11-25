package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.AgendaItem;
import com.examly.springapp.model.Meeting;
import com.examly.springapp.repository.AgendaItemRepository;
import com.examly.springapp.repository.MeetingRepository;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final AgendaItemRepository agendaItemRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository, AgendaItemRepository agendaItemRepository) {
        this.meetingRepository = meetingRepository;
        this.agendaItemRepository = agendaItemRepository;
    }

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAllWithAgendaItems();
    }

    @Override
    public Optional<Meeting> getMeetingById(Long id) {
        return meetingRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<AgendaItem> addAgendaItem(Long meetingId, AgendaItem agendaItem) {
        Optional<Meeting> meeting = meetingRepository.findById(meetingId);
        if (meeting.isEmpty()) {
            return Optional.empty();
        }
        agendaItem.setId(null);
        meeting.get().addAgendaItem(agendaItem);
        return Optional.of(agendaItemRepository.save(agendaItem));
    }

    @Transactional
    @Override
    public boolean removeAgendaItem(Long meetingId, Long agendaItemId) {
        Optional<Meeting> meeting = meetingRepository.findById(meetingId);
        if (meeting.isEmpty()) {
            return false;
        }
        for (AgendaItem x : meeting.get().getAgendaItems()) {
            if (x.getId().equals(agendaItemId)) {
                meeting.get().removeAgendaItem(x);
                meetingRepository.save(meeting.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
