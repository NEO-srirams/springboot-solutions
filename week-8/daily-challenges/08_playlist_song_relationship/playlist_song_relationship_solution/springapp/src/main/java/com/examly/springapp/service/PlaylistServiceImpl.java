package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Playlist;
import com.examly.springapp.repository.PlaylistRepository;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }
}
