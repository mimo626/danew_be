package com.example.danew_spring.auth.domain;

import com.example.danew_spring.auth.dto.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Slf4j
public class User {
    @Id
    private String userId;
    private String password;
    private String name;
    private Integer age;
    private String gender;
    private String createdAt;
    @Convert(converter = StringListConverter.class)
    private List<String> keywordList;
    @Convert(converter = StringListConverter.class)
    private List<String> customList;
    // ⭐ FCM 토큰 필드 추가
    @Column(length = 500)
    private String fcmToken;


    public User(String name, String userId, String password, Integer age,
                String gender, String createdAt, List<String> keywordList, List<String> customList,
                String fcmToken) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.createdAt = createdAt;
        this.keywordList = keywordList;
        this.customList = customList;
        this.fcmToken = fcmToken;
    }

    // 토큰 업데이트를 위한 메서드
    public void updateFcmToken(String token) {
        this.fcmToken = token;
    }

    // 로그아웃 시 토큰 삭제를 위한 메서드
    public void clearFcmToken() {
        this.fcmToken = null;
    }
}
