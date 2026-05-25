package com.example.phonepaywalletbackend.service;

import com.example.phonepaywalletbackend.entity.User;
import com.example.phonepaywalletbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= REGISTER =================
    public User registerUser(User user) {

        // check phone already exists
        User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (existingUser != null) {
            throw new RuntimeException("Phone number already exists");
        }

        return userRepository.save(user);
    }

    // ================= LOGIN =================
    public User login(String phoneNumber, String pin) {

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPin().equals(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        return user;
    }
}