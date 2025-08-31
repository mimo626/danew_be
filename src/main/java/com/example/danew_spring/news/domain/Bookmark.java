package com.example.danew_spring.news.domain;

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
public class Bookmark {

    @Id
    @Column(length = 45)
    private String userId;   // PK (단일)

    @Column(length = 45, nullable = false)
    private String articleId;

    private LocalDate bookmarkedAt;

    // 생성자
    public Bookmark(String userId, String articleId, LocalDate bookmarkedAt) {
        this.userId = userId;
        this.articleId = articleId;
        this.bookmarkedAt = bookmarkedAt;
    }
}
