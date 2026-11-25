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
import com.examly.springapp.model.IdCard;
import com.examly.springapp.service.IdCardService;

@RestController
@RequestMapping("/api/id-cards")
public class IdCardController {

    private final IdCardService idCardService;

    public IdCardController(IdCardService idCardService) {
        this.idCardService = idCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIdCardById(@PathVariable Long id) {
        Optional<IdCard> idCard = idCardService.getIdCardById(id);
        if (idCard.isPresent()) {
            return ResponseEntity.ok(idCard.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "IdCard not found with id " + id));
    }
}
