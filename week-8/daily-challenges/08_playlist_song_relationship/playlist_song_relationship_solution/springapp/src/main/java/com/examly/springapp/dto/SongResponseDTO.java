package com.examly.springapp.dto;

public class SongResponseDTO {

    private Long id;
    private String title;
    private String artist;
    private Integer durationSeconds;
    private Long playlistId;
    private String playlistName;

    public SongResponseDTO() {
    }

    public SongResponseDTO(Long id, String title, String artist, Integer durationSeconds, Long playlistId, String playlistName) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.playlistId = playlistId;
        this.playlistName = playlistName;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
