package com.example.danew_spring.news.service;

import com.example.danew_spring.news.domain.News;
import com.example.danew_spring.news.repository.NewsRepository;
import com.example.danew_spring.news.repository.NewsSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsSummaryRepository newsSummaryRepository;

    public void save(News news) {
        newsRepository.save(news);
    }
}
