package com.example.phonepaywalletbackend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String phoneNumber;
    private String pin;
}