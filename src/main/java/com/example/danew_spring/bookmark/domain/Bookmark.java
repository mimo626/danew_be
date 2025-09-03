package com.example.danew_spring.bookmark.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Slf4j
@Data
@Entity
public class Bookmark {

    @EmbeddedId
    private BookmarkId id;

    private LocalDate bookmarkedAt;

    protected Bookmark() {}

    public Bookmark(String userId, String articleId, LocalDate bookmarkedAt) {
        this.id = new BookmarkId(userId, articleId);
        this.bookmarkedAt = bookmarkedAt;
    }

    // getter/setter
}
