package com.example.phonepaywalletbackend.dto;

public class LoginResponse {

    private Long id;
    private String name;
    private String phoneNumber;
    private String upiId;

    public LoginResponse(Long id, String name, String phoneNumber, String upiId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.upiId = upiId;
    }

    // getters only
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getUpiId() { return upiId; }
}