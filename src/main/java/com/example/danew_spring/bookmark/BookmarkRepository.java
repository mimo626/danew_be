package com.example.danew_spring.bookmark;

import com.example.danew_spring.bookmark.domain.Bookmark;
import com.example.danew_spring.bookmark.domain.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findById(BookmarkId bookmarkId);

    List<Bookmark> findByIdUserId(String userId); // EmbeddedId 안 userId 기준 조회

    void deleteById(BookmarkId bookmarkId);
}
