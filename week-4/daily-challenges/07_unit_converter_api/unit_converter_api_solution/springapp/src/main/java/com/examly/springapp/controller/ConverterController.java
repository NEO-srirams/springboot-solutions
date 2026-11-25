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

@RestController
@RequestMapping("/api/convert")
public class ConverterController {

    private static final List<String> TEMPERATURE_UNITS = List.of("C", "F", "K");
    private static final Map<String, Double> METERS_PER_UNIT = Map.of("km", 1000.0, "m", 1.0, "cm", 0.01);

    @GetMapping("/units")
    public ResponseEntity<Map<String, List<String>>> getUnits() {
        Map<String, List<String>> units = new LinkedHashMap<>();
        units.put("temperature", TEMPERATURE_UNITS);
        units.put("length", List.of("km", "m", "cm"));
        return ResponseEntity.ok(units);
    }

    @GetMapping("/temperature")
    public ResponseEntity<?> convertTemperature(@RequestParam double value, @RequestParam String from, @RequestParam String to) {
        String f = from.toUpperCase();
        String t = to.toUpperCase();
        if (!TEMPERATURE_UNITS.contains(f)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Unsupported unit: " + from));
        }
        if (!TEMPERATURE_UNITS.contains(t)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Unsupported unit: " + to));
        }
        double celsius = f.equals("C") ? value : f.equals("F") ? (value - 32) * 5 / 9 : value - 273.15;
        double result = t.equals("C") ? celsius : t.equals("F") ? celsius * 9 / 5 + 32 : celsius + 273.15;
        result = Math.round(result * 100.0) / 100.0;
        return ResponseEntity.ok(new ConversionResponseDTO("temperature", value, f, t, result));
    }

    @GetMapping("/length/{from}/{to}")
    public ResponseEntity<?> convertLength(@PathVariable String from, @PathVariable String to, @RequestParam double value) {
        String f = from.toLowerCase();
        String t = to.toLowerCase();
        if (!METERS_PER_UNIT.containsKey(f)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Unsupported unit: " + from));
        }
        if (!METERS_PER_UNIT.containsKey(t)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Unsupported unit: " + to));
        }
        double result = value * METERS_PER_UNIT.get(f) / METERS_PER_UNIT.get(t);
        result = Math.round(result * 100.0) / 100.0;
        return ResponseEntity.ok(new ConversionResponseDTO("length", value, f, t, result));
    }
}
