package com.example.danew_spring.alarm.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Slf4j
public class Alarm {

    @Id
    @Column(length = 45)
    private String alarmId;   // PK

    @Column(length = 45, nullable = false)
    private String userId;    // 알람 대상 유저

    @Column(length = 255, nullable = false)
    private String message;   // 알림 내용

    private LocalDateTime createdAt;  // 알림 생성 시각

    private Boolean isRead;   // 읽음 여부

    // 생성자
    public Alarm(String alarmId, String userId, String message,
                 LocalDateTime createdAt, Boolean isRead) {
        this.alarmId = alarmId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }
}
