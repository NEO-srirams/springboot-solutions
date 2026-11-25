package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT DISTINCT x FROM Member x LEFT JOIN FETCH x.loans ORDER BY x.id")
    List<Member> findAllWithLoans();
}
