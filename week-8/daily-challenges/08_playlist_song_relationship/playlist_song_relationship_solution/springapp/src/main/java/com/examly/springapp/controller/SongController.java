package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.SongResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Song;
import com.examly.springapp.service.SongService;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSongById(@PathVariable Long id) {
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            return ResponseEntity.ok(toSongResponse(song.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Song not found with id " + id));
    }

    private SongResponseDTO toSongResponse(Song x) {
        return new SongResponseDTO(x.getId(), x.getTitle(), x.getArtist(), x.getDurationSeconds(), x.getPlaylist().getId(), x.getPlaylist().getName());
    }
}
