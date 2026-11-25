package com.examly.springapp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.*;
import com.examly.springapp.model.*;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final List<Notice> notices = new ArrayList<>();

    public NoticeController() {
        notices.add(new Notice(1L, "Holiday Notice", "College will remain closed on Friday", "Admin Office"));
        notices.add(new Notice(2L, "Exam Timetable", "Semester exams start on 5th December", "Exam Cell"));
        notices.add(new Notice(3L, "Library Timing", "Library will stay open till 8 PM", "Librarian"));
    }

    @GetMapping
    public ResponseEntity<List<Notice>> getAllNotices() {
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeById(@PathVariable Long id) {
        for (Notice n : notices) {
            if (n.getId().equals(id)) {
                return ResponseEntity.ok(n);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Notice not found with id " + id));
    }
}
