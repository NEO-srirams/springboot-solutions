package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Ward;
import com.examly.springapp.repository.WardRepository;

@Service
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;

    public WardServiceImpl(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    @Override
    public Ward saveWard(Ward ward) {
        return wardRepository.save(ward);
    }

    @Override
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    @Override
    public Optional<Ward> getWardById(Long id) {
        return wardRepository.findById(id);
    }
}
