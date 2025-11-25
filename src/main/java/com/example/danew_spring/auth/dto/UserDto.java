package com.example.danew_spring.auth.dto;

import com.example.danew_spring.auth.domain.User;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class UserDto {
    private String name;
    private String userId;
    private String password;
    private Integer age;
    private String gender;
    private String createdAt;
    @Convert(converter = StringListConverter.class)
    private List<String> keywordList;
    @Convert(converter = StringListConverter.class)
    private List<String> customList;
    private String fcmToken;

    public User toEntity() {return new User(name, userId, password, age,
            gender, createdAt, keywordList, customList, fcmToken);}
}
