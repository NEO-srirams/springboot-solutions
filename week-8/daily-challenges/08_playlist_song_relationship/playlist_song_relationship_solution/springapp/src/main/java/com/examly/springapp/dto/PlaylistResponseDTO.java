package com.examly.springapp.dto;

public class PlaylistResponseDTO {

    private Long id;
    private String name;
    private String owner;
    private Integer songsCount;

    public PlaylistResponseDTO() {
    }

    public PlaylistResponseDTO(Long id, String name, String owner, Integer songsCount) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.songsCount = songsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
    }
}
