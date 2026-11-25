package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT DISTINCT x FROM Meeting x LEFT JOIN FETCH x.agendaItems ORDER BY x.id")
    List<Meeting> findAllWithAgendaItems();
}
