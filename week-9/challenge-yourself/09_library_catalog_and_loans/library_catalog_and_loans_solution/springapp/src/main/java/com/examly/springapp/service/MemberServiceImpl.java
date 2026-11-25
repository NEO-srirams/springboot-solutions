package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Loan;
import com.examly.springapp.model.Member;
import com.examly.springapp.repository.LoanRepository;
import com.examly.springapp.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public MemberServiceImpl(MemberRepository memberRepository, LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAllWithLoans();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Loan> addLoan(Long memberId, Loan loan) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            return Optional.empty();
        }
        loan.setId(null);
        member.get().addLoan(loan);
        return Optional.of(loanRepository.save(loan));
    }

    @Transactional
    @Override
    public boolean removeLoan(Long memberId, Long loanId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            return false;
        }
        for (Loan x : member.get().getLoans()) {
            if (x.getId().equals(loanId)) {
                member.get().removeLoan(x);
                memberRepository.save(member.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
