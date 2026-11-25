package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.AgendaItemRequestDTO;
import com.examly.springapp.dto.AgendaItemResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.MeetingRequestDTO;
import com.examly.springapp.dto.MeetingResponseDTO;
import com.examly.springapp.model.AgendaItem;
import com.examly.springapp.model.Meeting;
import com.examly.springapp.service.MeetingService;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody MeetingRequestDTO request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (request.getMeetingDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Meeting date is required"));
        }
        if (request.getAgendaItems() != null) {
            for (AgendaItemRequestDTO item : request.getAgendaItems()) {
            if (item.getTopic() == null || item.getTopic().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Agenda item topic is required"));
            }
            if (item.getMinutes() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Agenda item minutes is required"));
            }
            }
        }
        Meeting meeting = new Meeting();
        meeting.setTitle(request.getTitle());
        meeting.setMeetingDate(request.getMeetingDate());
        if (request.getAgendaItems() != null) {
            for (AgendaItemRequestDTO item : request.getAgendaItems()) {
                AgendaItem agendaItem = new AgendaItem();
                agendaItem.setTopic(item.getTopic());
                agendaItem.setMinutes(item.getMinutes());
                meeting.addAgendaItem(agendaItem);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toMeetingResponse(meetingService.saveMeeting(meeting)));
    }

    @GetMapping
    public ResponseEntity<List<MeetingResponseDTO>> getAllMeetings() {
        List<MeetingResponseDTO> result = new ArrayList<>();
        for (Meeting x : meetingService.getAllMeetings()) {
            result.add(toMeetingResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeetingById(@PathVariable Long id) {
        Optional<Meeting> meeting = meetingService.getMeetingById(id);
        if (meeting.isPresent()) {
            return ResponseEntity.ok(toMeetingResponse(meeting.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Meeting not found with id " + id));
    }

    @PostMapping("/{id}/agendaItems")
    public ResponseEntity<?> addAgendaItemToMeeting(@PathVariable Long id, @RequestBody AgendaItemRequestDTO item) {
        if (item.getTopic() == null || item.getTopic().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Topic is required"));
        }
        if (item.getMinutes() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minutes is required"));
        }
        AgendaItem agendaItem = new AgendaItem();
        agendaItem.setTopic(item.getTopic());
        agendaItem.setMinutes(item.getMinutes());
        Optional<AgendaItem> saved = meetingService.addAgendaItem(id, agendaItem);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toAgendaItemResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Meeting not found with id " + id));
    }

    @DeleteMapping("/{id}/agendaItems/{agendaItemId}")
    public ResponseEntity<?> removeAgendaItemFromMeeting(@PathVariable Long id, @PathVariable Long agendaItemId) {
        if (meetingService.getMeetingById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Meeting not found with id " + id));
        }
        if (meetingService.removeAgendaItem(id, agendaItemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "AgendaItem not found with id " + agendaItemId + " in Meeting " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long id) {
        if (meetingService.getMeetingById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Meeting not found with id " + id));
        }
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    private AgendaItemResponseDTO toAgendaItemResponse(AgendaItem x) {
        return new AgendaItemResponseDTO(x.getId(), x.getTopic(), x.getMinutes());
    }

    private MeetingResponseDTO toMeetingResponse(Meeting x) {
        List<AgendaItemResponseDTO> items = new ArrayList<>();
        for (AgendaItem y : x.getAgendaItems()) {
            items.add(toAgendaItemResponse(y));
        }
        items.sort(Comparator.comparing(AgendaItemResponseDTO::getId));
        return new MeetingResponseDTO(x.getId(), x.getTitle(), x.getMeetingDate(), items);
    }
}
