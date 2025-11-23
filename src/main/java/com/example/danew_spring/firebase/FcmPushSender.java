package com.example.danew_spring.firebase;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmPushSender {

    /**
     * 1. 특정 기기(Token) 하나에 알림 전송
     */
    public void sendPushNotification(FcmSendDto dto) {
        try {
            Message message = Message.builder()
                    .setToken(dto.getToken())
                    .setNotification(Notification.builder()
                            .setTitle(dto.getTitle())
                            .setBody(dto.getBody())
                            .build())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder()
                                    .setClickAction("OPEN_ACTIVITY")
                                    .build())
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ 개별 FCM 전송 성공: {}", response);

        } catch (FirebaseMessagingException e) {
            log.error("❌ 개별 FCM 전송 실패: {}", e.getMessage());
            throw new RuntimeException("FCM 전송 오류", e);
        }
    }

    /**
     * 2. 특정 주제(Topic)를 구독한 모든 사람에게 전송 (뉴스, 공지사항 등)
     * 예: topicName = "daily_news"
     */
    public void sendTopicNotification(String topicName, String title, String body) {
        try {
            Message message = Message.builder()
                    .setTopic(topicName) // Token 대신 Topic 설정
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ 토픽({}) 전송 성공: {}", topicName, response);

        } catch (FirebaseMessagingException e) {
            log.error("❌ 토픽 전송 실패: {}", e.getMessage());
        }
    }
}