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

import com.examly.springapp.dto.PlaylistResponseDTO;
import com.examly.springapp.dto.SongResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Playlist;
import com.examly.springapp.model.Song;
import com.examly.springapp.service.PlaylistService;
import com.examly.springapp.service.SongService;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;

    public PlaylistController(PlaylistService playlistService, SongService songService) {
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getName() == null || playlist.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (playlist.getOwner() == null || playlist.getOwner().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Owner is required"));
        }
        playlist.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toPlaylistResponse(playlistService.savePlaylist(playlist)));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        List<PlaylistResponseDTO> result = new ArrayList<>();
        for (Playlist x : playlistService.getAllPlaylists()) {
            result.add(toPlaylistResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long id) {
        Optional<Playlist> playlist = playlistService.getPlaylistById(id);
        if (playlist.isPresent()) {
            return ResponseEntity.ok(toPlaylistResponse(playlist.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Playlist not found with id " + id));
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<?> getSongsByPlaylist(@PathVariable Long id) {
        if (playlistService.getPlaylistById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Playlist not found with id " + id));
        }
        List<SongResponseDTO> result = new ArrayList<>();
        for (Song x : songService.getSongsByPlaylistId(id)) {
            result.add(toSongResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<?> createSongForPlaylist(@PathVariable Long id, @RequestBody Song song) {
        if (song.getTitle() == null || song.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (song.getArtist() == null || song.getArtist().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Artist is required"));
        }
        if (song.getDurationSeconds() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Duration seconds is required"));
        }
        Optional<Song> saved = songService.createSongForPlaylist(id, song);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toSongResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Playlist not found with id " + id));
    }

    private PlaylistResponseDTO toPlaylistResponse(Playlist x) {
        return new PlaylistResponseDTO(x.getId(), x.getName(), x.getOwner(), x.getSongs().size());
    }

    private SongResponseDTO toSongResponse(Song x) {
        return new SongResponseDTO(x.getId(), x.getTitle(), x.getArtist(), x.getDurationSeconds(), x.getPlaylist().getId(), x.getPlaylist().getName());
    }
}
