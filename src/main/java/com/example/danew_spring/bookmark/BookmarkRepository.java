package com.example.danew_spring.bookmark;

import com.example.danew_spring.news.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserId(String userId);

    void deleteByUserIdAndArticleId(String userId, String articleId);
}
