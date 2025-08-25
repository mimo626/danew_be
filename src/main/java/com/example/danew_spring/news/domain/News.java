package com.example.danew_spring.news.domain;
import com.example.danew_spring.auth.dto.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(length = 255, unique = true)
    private String content;   // unique constraint (PK로 쓰고 싶다면 복합키로 설정 가능)

    @Column(length = 45)
    private String pubDate;

    @Column(length = 45)
    private String pubDateTz;

    @Column(length = 255)
    private String imageUrl;

    @Column(length = 255)
    private String videoUrl;

    @Column(length = 45)
    private String sourceId;

    @Column(length = 45)
    private String sourceName;

    private Integer sourcePriority;

    @Column(length = 255)
    private String sourceUrl;

    @Column(length = 45)
    private String sourceIcon;

    @Column(length = 45)
    private String language;

    @Convert(converter = StringListConverter.class)
    private List<String> country;

    @Convert(converter = StringListConverter.class)
    private List<String> category;

    @Column(length = 45)
    private String sentiment;

    @Column(length = 45)
    private String sentimentStats;

    @Column(length = 45)
    private String aiTag;

    @Column(length = 45)
    private String aiRegion;

    @Column(length = 45)
    private String aiOrg;

    private Boolean duplicate;

    public News(String articleId, String title, String link,
                List<String> keywords, List<String> creator, String description,
                String content, String pubDate, String pubDateTz,
                String imageUrl, String videoUrl, String sourceId,
                String sourceName, Integer sourcePriority, String sourceUrl,
                String sourceIcon, String language, List<String> country,
                List<String> category, String sentiment, String sentimentStats,
                String aiTag, String aiRegion, String aiOrg,
                Boolean duplicate) {
        this.articleId = articleId;
        this.title = title;
        this.link = link;
        this.keywords = keywords;
        this.creator = creator;
        this.description = description;
        this.content = content;
        this.pubDate = pubDate;
        this.pubDateTz = pubDateTz;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.sourcePriority = sourcePriority;
        this.sourceUrl = sourceUrl;
        this.sourceIcon = sourceIcon;
        this.language = language;
        this.country = country;
        this.category = category;
        this.sentiment = sentiment;
        this.sentimentStats = sentimentStats;
        this.aiTag = aiTag;
        this.aiRegion = aiRegion;
        this.aiOrg = aiOrg;
        this.duplicate = duplicate;
    }
}
