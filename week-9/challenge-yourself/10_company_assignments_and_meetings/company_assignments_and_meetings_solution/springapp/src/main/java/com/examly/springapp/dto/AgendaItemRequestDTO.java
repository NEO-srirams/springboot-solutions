package com.examly.springapp.dto;

public class AgendaItemRequestDTO {

    private String topic;
    private Integer minutes;

    public AgendaItemRequestDTO() {
    }

    public AgendaItemRequestDTO(String topic, Integer minutes) {
        this.topic = topic;
        this.minutes = minutes;
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
