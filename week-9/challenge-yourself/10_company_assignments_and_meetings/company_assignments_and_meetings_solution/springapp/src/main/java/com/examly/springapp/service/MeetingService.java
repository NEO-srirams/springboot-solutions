package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.AgendaItem;
import com.examly.springapp.model.Meeting;

public interface MeetingService {

    Meeting saveMeeting(Meeting meeting);

    List<Meeting> getAllMeetings();

    Optional<Meeting> getMeetingById(Long id);

    Optional<AgendaItem> addAgendaItem(Long meetingId, AgendaItem agendaItem);

    boolean removeAgendaItem(Long meetingId, Long agendaItemId);

    void deleteMeeting(Long id);
}
