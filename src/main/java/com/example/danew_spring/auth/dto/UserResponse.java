package com.example.danew_spring.auth.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String token;
    private String createdAt;

    public UserResponse(String userId, String token, String createdAt) {
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
    }
}
