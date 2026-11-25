package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.AgendaItem;

public interface AgendaItemRepository extends JpaRepository<AgendaItem, Long> {
}
