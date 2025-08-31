package com.example.danew_spring.news;

import com.example.danew_spring.news.domain.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public News save(News news) {
        return newsRepository.save(news);
    }
}
