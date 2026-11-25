package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Member;

public interface MemberService {

    Member saveMember(Member member);

    List<Member> getAllMembers();

    Optional<Member> getMemberById(Long id);
}
