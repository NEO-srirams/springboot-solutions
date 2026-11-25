package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Profile;
import com.examly.springapp.model.User;

public interface ProfileService {

    Profile saveProfile(Profile profile);

    List<Profile> getAllProfiles();

    Optional<Profile> getProfileById(Long id);

    Optional<Profile> getProfileByUserId(Long userId);

    boolean userHasProfile(Long userId);

    Optional<Profile> createProfileForUser(Long userId, Profile profile);
}
