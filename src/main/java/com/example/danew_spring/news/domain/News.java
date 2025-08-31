package com.example.danew_spring.news.domain;
import com.example.danew_spring.auth.dto.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@ToString
@Slf4j
public class News {

    @Id
    @Column(length = 45)
    private String articleId;   // PK

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String link;

    @Convert(converter = StringListConverter.class)
    private List<String> keywords;

    @Convert(converter = StringListConverter.class)
    private List<String> creator;

    @Column(length = 45)
    private String description;

    @Column(length = 45)
    private String pubDate;

    @Column(length = 255)
    private String imageUrl;

    @Column(length = 45)
    private String sourceName;

    @Column(length = 45)
    private String language;

    @Convert(converter = StringListConverter.class)
    private List<String> category;

    private LocalDate savedAt;

    public News(String articleId, String title, String link,
                List<String> keywords, List<String> creator, String description,
                String pubDate, String imageUrl, String sourceName,
                String language, List<String> category, LocalDate savedAt) {
        this.articleId = articleId;
        this.title = title;
        this.link = link;
        this.keywords = keywords;
        this.creator = creator;
        this.description = description;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
        this.sourceName = sourceName;
        this.language = language;
        this.category = category;
        this.savedAt = savedAt;
    }
}
