package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByPlaylistId(Long playlistId);
}
