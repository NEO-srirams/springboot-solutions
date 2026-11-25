package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.LoanRequestDTO;
import com.examly.springapp.dto.LoanResponseDTO;
import com.examly.springapp.dto.MemberRequestDTO;
import com.examly.springapp.dto.MemberResponseDTO;
import com.examly.springapp.model.Loan;
import com.examly.springapp.model.Member;
import com.examly.springapp.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (request.getLoans() != null) {
            for (LoanRequestDTO item : request.getLoans()) {
            if (item.getBookTitle() == null || item.getBookTitle().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Loan book title is required"));
            }
            if (item.getBorrowedOn() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Loan borrowed on is required"));
            }
            if (item.getDueOn() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Loan due on is required"));
            }
            }
        }
        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        if (request.getLoans() != null) {
            for (LoanRequestDTO item : request.getLoans()) {
                Loan loan = new Loan();
                loan.setBookTitle(item.getBookTitle());
                loan.setBorrowedOn(item.getBorrowedOn());
                loan.setDueOn(item.getDueOn());
                member.addLoan(loan);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toMemberResponse(memberService.saveMember(member)));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> result = new ArrayList<>();
        for (Member x : memberService.getAllMembers()) {
            result.add(toMemberResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberService.getMemberById(id);
        if (member.isPresent()) {
            return ResponseEntity.ok(toMemberResponse(member.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Member not found with id " + id));
    }

    @PostMapping("/{id}/loans")
    public ResponseEntity<?> addLoanToMember(@PathVariable Long id, @RequestBody LoanRequestDTO item) {
        if (item.getBookTitle() == null || item.getBookTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Book title is required"));
        }
        if (item.getBorrowedOn() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Borrowed on is required"));
        }
        if (item.getDueOn() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Due on is required"));
        }
        Loan loan = new Loan();
        loan.setBookTitle(item.getBookTitle());
        loan.setBorrowedOn(item.getBorrowedOn());
        loan.setDueOn(item.getDueOn());
        Optional<Loan> saved = memberService.addLoan(id, loan);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toLoanResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Member not found with id " + id));
    }

    @DeleteMapping("/{id}/loans/{loanId}")
    public ResponseEntity<?> removeLoanFromMember(@PathVariable Long id, @PathVariable Long loanId) {
        if (memberService.getMemberById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Member not found with id " + id));
        }
        if (memberService.removeLoan(id, loanId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Loan not found with id " + loanId + " in Member " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        if (memberService.getMemberById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Member not found with id " + id));
        }
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    private LoanResponseDTO toLoanResponse(Loan x) {
        return new LoanResponseDTO(x.getId(), x.getBookTitle(), x.getBorrowedOn(), x.getDueOn());
    }

    private MemberResponseDTO toMemberResponse(Member x) {
        List<LoanResponseDTO> items = new ArrayList<>();
        for (Loan y : x.getLoans()) {
            items.add(toLoanResponse(y));
        }
        items.sort(Comparator.comparing(LoanResponseDTO::getId));
        return new MemberResponseDTO(x.getId(), x.getName(), x.getEmail(), items);
    }
}
