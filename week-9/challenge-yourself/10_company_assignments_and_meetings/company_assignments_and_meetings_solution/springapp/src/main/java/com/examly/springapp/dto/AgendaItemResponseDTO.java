package com.examly.springapp.dto;

public class AgendaItemResponseDTO {

    private Long id;
    private String topic;
    private Integer minutes;

    public AgendaItemResponseDTO() {
    }

    public AgendaItemResponseDTO(Long id, String topic, Integer minutes) {
        this.id = id;
        this.topic = topic;
        this.minutes = minutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
