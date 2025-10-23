package com.example.danew_spring.diary.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Slf4j
public class Diary {

    @Id
    @Column(length = 45)
    private String diaryId;   // PK

    @Column(length = 45, nullable = false)
    private String userId;

    private LocalDate createdAt;

    @Column(columnDefinition = "TEXT(1000)")
    private String content;

    // 전체 필드 생성자
    public Diary(String diaryId, String userId, LocalDate createdAt, String content) {
        this.diaryId = diaryId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.content = content;
    }
}
