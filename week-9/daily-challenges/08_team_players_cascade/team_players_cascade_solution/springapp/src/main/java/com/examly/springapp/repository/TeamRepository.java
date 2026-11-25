package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT DISTINCT x FROM Team x LEFT JOIN FETCH x.players ORDER BY x.id")
    List<Team> findAllWithPlayers();
}
