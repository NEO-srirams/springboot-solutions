package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
