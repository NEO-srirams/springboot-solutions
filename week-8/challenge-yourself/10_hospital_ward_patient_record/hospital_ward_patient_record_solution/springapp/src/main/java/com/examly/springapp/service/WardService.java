package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Ward;

public interface WardService {

    Ward saveWard(Ward ward);

    List<Ward> getAllWards();

    Optional<Ward> getWardById(Long id);
}
