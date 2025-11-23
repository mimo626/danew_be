package com.example.danew_spring.manage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Slf4j
public class Question {

    @Id
    @Column(length = 45)
    private String questionId;   // PK

    @Column(columnDefinition = "TEXT(1000)")
    private String question;

    @Column(columnDefinition = "TEXT(1000)")
    private String answer;

    // 전체 필드 생성자
    public Question(String questionId, String question, String answer) {
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
    }
}
