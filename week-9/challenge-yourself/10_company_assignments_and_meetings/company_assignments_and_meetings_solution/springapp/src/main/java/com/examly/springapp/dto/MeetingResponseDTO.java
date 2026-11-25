package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;

public class MeetingResponseDTO {

    private Long id;
    private String title;
    private LocalDate meetingDate;
    private List<AgendaItemResponseDTO> agendaItems;

    public MeetingResponseDTO() {
    }

    public MeetingResponseDTO(Long id, String title, LocalDate meetingDate, List<AgendaItemResponseDTO> agendaItems) {
        this.id = id;
        this.title = title;
        this.meetingDate = meetingDate;
        this.agendaItems = agendaItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public List<AgendaItemResponseDTO> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItemResponseDTO> agendaItems) {
        this.agendaItems = agendaItems;
    }
}
