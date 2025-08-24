package com.example.danew_spring.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}

