package com.example.danew_spring.news.domain;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@ToString
@Slf4j
@Data
public class NewsSummary {

    @Id
    @Column(length = 255)
    private String articleId;   // PK

    @Column(columnDefinition = "TEXT(1000)")
    private String summary;


    public NewsSummary(String articleId, String summary) {
        this.articleId = articleId;
        this.summary = summary;
    }
}
