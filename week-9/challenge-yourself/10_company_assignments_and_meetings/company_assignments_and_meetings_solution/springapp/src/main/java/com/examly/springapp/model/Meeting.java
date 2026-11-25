package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AgendaItem> agendaItems = new ArrayList<>();

    public Meeting() {
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

    public List<AgendaItem> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }

    public void addAgendaItem(AgendaItem agendaItem) {
        agendaItems.add(agendaItem);
        agendaItem.setMeeting(this);
    }

    public void removeAgendaItem(AgendaItem agendaItem) {
        agendaItems.remove(agendaItem);
        agendaItem.setMeeting(null);
    }

}
