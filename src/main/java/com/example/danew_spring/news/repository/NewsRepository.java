package com.example.danew_spring.news.repository;

import com.example.danew_spring.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    News findNewsById(String id);
    List<News> findByIdIn(List<String> articleId);
}
