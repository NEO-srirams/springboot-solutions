package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;

public class MeetingRequestDTO {

    private String title;
    private LocalDate meetingDate;
    private List<AgendaItemRequestDTO> agendaItems;

    public MeetingRequestDTO() {
    }

    public MeetingRequestDTO(String title, LocalDate meetingDate, List<AgendaItemRequestDTO> agendaItems) {
        this.title = title;
        this.meetingDate = meetingDate;
        this.agendaItems = agendaItems;
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

    public List<AgendaItemRequestDTO> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItemRequestDTO> agendaItems) {
        this.agendaItems = agendaItems;
    }
}
