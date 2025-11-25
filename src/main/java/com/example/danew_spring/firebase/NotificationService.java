package com.example.danew_spring.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class NotificationService {
    private final FcmPushSender fcmPushSender;

    // 예: 사용자가 입장했을 때 알림 보내기
    public void sendEntryNotification(String fcmToken, String title, String content) {
        if (fcmToken == null || fcmToken.isEmpty()) return;

        FcmSendDto fcmDto = FcmSendDto.builder()
                .token(fcmToken)
                .title(title)
                .body(content)
                .build();

        fcmPushSender.sendPushNotification(fcmDto);
    }
}