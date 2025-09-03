package com.example.danew_spring.bookmark.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class BookmarkId implements Serializable {
    private String userId;
    private String articleId;

    public BookmarkId(String userId, String articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
