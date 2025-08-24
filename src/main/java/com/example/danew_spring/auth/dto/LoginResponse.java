package com.example.danew_spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private boolean success; // 로그인 성공 여부
    private String message;  // 실패 이유 또는 성공 메시지
}