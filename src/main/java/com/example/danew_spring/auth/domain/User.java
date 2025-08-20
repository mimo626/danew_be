package com.example.danew_spring.auth.domain;

import com.example.danew_spring.auth.dto.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    @Convert(converter = StringListConverter.class)
    private List<String> keywordList;
    @Convert(converter = StringListConverter.class)
    private List<String> customList;


    public User(String name, String userId, String password, Integer age,
                String gender, LocalDateTime createdAt, List<String> keywordList, List<String> customList) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.createdAt = createdAt;
        this.keywordList = keywordList;
        this.customList = customList;
    }
}
