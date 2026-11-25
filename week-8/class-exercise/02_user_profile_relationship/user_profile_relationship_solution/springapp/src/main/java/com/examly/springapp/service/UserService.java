package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.User;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);
}
