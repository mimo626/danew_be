package com.example.danew_spring.news;

import com.example.danew_spring.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    List<News> findByArticleIdIn(List<String> articleId);
}
