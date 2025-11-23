package com.example.danew_spring.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestHeader("X-FCM-Token") String token, // 헤더로 토큰 받기
            @RequestParam String nickname
    ) {
        notificationService.sendEntryNotification(token, nickname);
        return ResponseEntity.ok("알림 전송 요청 완료");
    }
}