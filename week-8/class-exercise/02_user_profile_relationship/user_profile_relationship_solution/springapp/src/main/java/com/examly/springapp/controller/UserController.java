package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.Profile;
import com.examly.springapp.service.UserService;
import com.examly.springapp.service.ProfileService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;

    public UserController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Username is required"));
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        user.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "User not found with id " + id));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getProfileByUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "User not found with id " + id));
        }
        Optional<Profile> profile = profileService.getProfileByUserId(id);
        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "No Profile found for User with id " + id));
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<?> createProfileForUser(@PathVariable Long id, @RequestBody Profile profile) {
        if (profile.getFullName() == null || profile.getFullName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Full name is required"));
        }
        if (profile.getPhone() == null || profile.getPhone().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Phone is required"));
        }
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "User not found with id " + id));
        }
        if (profileService.userHasProfile(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "User already has a profile"));
        }
        Optional<Profile> saved = profileService.createProfileForUser(id, profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
    }
}
