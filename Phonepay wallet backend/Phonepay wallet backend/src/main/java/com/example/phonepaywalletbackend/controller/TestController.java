package com.example.phonepaywalletbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "PhonePe Wallet Backend is running 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "TestController is working ✅";
    }
}