package com.example.danew_spring.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RequiredArgsConstructor
class NotificationService {
    private final FcmPushSender fcmPushSender;

    // 예: 사용자가 입장했을 때 알림 보내기
    public void sendEntryNotification(String fcmToken, String nickname) {
        if (fcmToken == null || fcmToken.isEmpty()) return;

        FcmSendDto fcmDto = FcmSendDto.builder()
                .token(fcmToken)
                .title("입장 알림")
                .body(nickname + "님이 채팅방에 입장했습니다!")
                .build();

        fcmPushSender.sendPushNotification(fcmDto);
    }
}