package com.example.danew_spring.news.repository;

import com.example.danew_spring.news.domain.NewsSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsSummaryRepository extends JpaRepository<NewsSummary, String> {
    Optional<NewsSummary> findByArticleId(String articleId);
}
