package com.example.danew_spring.auth.dto;

public class UserResponse {
    private String userId;
    private String createdAt;

    public UserResponse(String userId, String createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // getters/setters 생략
}
