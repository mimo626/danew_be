package com.example.danew_spring.firebase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmSendDto {
    private String token; // 받을 사람의 FCM 토큰
    private String title; // 알림 제목
    private String body;  // 알림 내용

    @Builder
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}