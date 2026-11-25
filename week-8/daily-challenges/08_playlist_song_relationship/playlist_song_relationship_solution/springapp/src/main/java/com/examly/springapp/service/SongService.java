package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Song;
import com.examly.springapp.model.Playlist;

public interface SongService {

    Song saveSong(Song song);

    List<Song> getAllSongs();

    Optional<Song> getSongById(Long id);

    List<Song> getSongsByPlaylistId(Long playlistId);

    Optional<Song> createSongForPlaylist(Long playlistId, Song song);
}
