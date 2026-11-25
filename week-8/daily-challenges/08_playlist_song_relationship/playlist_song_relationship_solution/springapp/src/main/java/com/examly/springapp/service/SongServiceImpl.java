package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Song;
import com.examly.springapp.model.Playlist;
import com.examly.springapp.repository.PlaylistRepository;
import com.examly.springapp.repository.SongRepository;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;

    public SongServiceImpl(SongRepository songRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public List<Song> getSongsByPlaylistId(Long playlistId) {
        return songRepository.findByPlaylistId(playlistId);
    }

    @Override
    public Optional<Song> createSongForPlaylist(Long playlistId, Song song) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isEmpty()) {
            return Optional.empty();
        }
        song.setId(null);
        playlist.get().addSong(song);
        return Optional.of(songRepository.save(song));
    }
}
