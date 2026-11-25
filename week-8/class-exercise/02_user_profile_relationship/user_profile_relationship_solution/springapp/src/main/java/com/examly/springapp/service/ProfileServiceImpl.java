package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Profile;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public boolean userHasProfile(Long userId) {
        return profileRepository.existsByUserId(userId);
    }

    @Override
    public Optional<Profile> createProfileForUser(Long userId, Profile profile) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        profile.setId(null);
        user.get().setProfile(profile);
        return Optional.of(profileRepository.save(profile));
    }
}
