package com.example.phonepaywalletbackend.controller;

import com.example.phonepaywalletbackend.dto.LoginRequest;
import com.example.phonepaywalletbackend.dto.LoginResponse;
import com.example.phonepaywalletbackend.entity.User;
import com.example.phonepaywalletbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175"
        }
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(
                    request.getPhoneNumber(),
                    request.getPin()
            );

            LoginResponse response = new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getPhoneNumber(),
                    user.getUpiId()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }}