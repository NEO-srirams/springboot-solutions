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
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final List<Feedback> feedbackList = new ArrayList<>();
    private long nextId = 1;

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback) {
        if (feedback.getStudentName() == null || feedback.getStudentName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student name is required"));
        }
        if (feedback.getCourse() == null || feedback.getCourse().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Course is required"));
        }
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Rating must be between 1 and 5"));
        }
        if (feedback.getComments() != null && feedback.getComments().length() > 200) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Comments must not exceed 200 characters"));
        }
        feedback.setId(nextId++);
        feedback.setStatus("NEW");
        feedbackList.add(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getFeedback(@RequestParam(required = false) String course) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback f : feedbackList) {
            if (course == null || f.getCourse().equalsIgnoreCase(course)) {
                result.add(f);
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/summary")
    public ResponseEntity<FeedbackSummaryDTO> getSummary() {
        int total = feedbackList.size();
        double sum = 0;
        for (Feedback f : feedbackList) {
            sum += f.getRating();
        }
        double average = total == 0 ? 0.0 : Math.round(sum / total * 100.0) / 100.0;
        return ResponseEntity.ok(new FeedbackSummaryDTO(total, average));
    }
}
