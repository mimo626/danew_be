package com.example.danew_spring.manage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Slf4j
public class Announce {

    @Id
    @Column(length = 45)
    private String announceId;

    @Column(columnDefinition = "TEXT(1000)")
    private String title;

    @Column(columnDefinition = "TEXT(1000)")
    private String content;

    private LocalDate createdAt;

    // 전체 필드 생성자
    public Announce(String announceId, String title, String content, LocalDate createdAt) {
        this.announceId = announceId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
