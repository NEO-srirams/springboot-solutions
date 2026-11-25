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
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 3;

    public TaskController() {
        tasks.add(new Task(1L, "Prepare lab record", "Complete the Java lab record", "PENDING"));
        tasks.add(new Task(2L, "Submit assignment", "Upload the Spring Boot assignment", "PENDING"));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Task task = null;
        for (Task item : tasks) {
            if (item.getId().equals(id)) {
                task = item;
                break;
            }
        }
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Task not found with id " + id));
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        task.setId(nextId++);
        task.setStatus("PENDING");
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updated) {
        Task task = null;
        for (Task item : tasks) {
            if (item.getId().equals(id)) {
                task = item;
                break;
            }
        }
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Task not found with id " + id));
        }
        if (updated.getTitle() == null || updated.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> markComplete(@PathVariable Long id) {
        Task task = null;
        for (Task item : tasks) {
            if (item.getId().equals(id)) {
                task = item;
                break;
            }
        }
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Task not found with id " + id));
        }
        task.setStatus("DONE");
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Task task = null;
        for (Task item : tasks) {
            if (item.getId().equals(id)) {
                task = item;
                break;
            }
        }
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Task not found with id " + id));
        }
        tasks.remove(task);
        return ResponseEntity.noContent().build();
    }
}
