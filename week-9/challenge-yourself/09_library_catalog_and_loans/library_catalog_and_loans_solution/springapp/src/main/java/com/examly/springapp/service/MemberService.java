package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Loan;
import com.examly.springapp.model.Member;

public interface MemberService {

    Member saveMember(Member member);

    List<Member> getAllMembers();

    Optional<Member> getMemberById(Long id);

    Optional<Loan> addLoan(Long memberId, Loan loan);

    boolean removeLoan(Long memberId, Long loanId);

    void deleteMember(Long id);
}
