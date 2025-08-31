package com.example.danew_spring.bookmark;

import com.example.danew_spring.news.NewsRepository;
import com.example.danew_spring.news.NewsService;
import com.example.danew_spring.news.domain.Bookmark;
import com.example.danew_spring.news.domain.News;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private NewsRepository newsRepository;

    public Bookmark save(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void  deleteByUserIdAndArticleId(String userId, String articleId) {
        bookmarkRepository.deleteByUserIdAndArticleId(userId, articleId);
    }
    public List<News> getUserBookmarks(String userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        List<String> articleIds = bookmarks.stream()
                .map(Bookmark::getArticleId)
                .toList();

        List<News> newsList = newsRepository.findByIdIn(articleIds);

        return newsList;
    }
}
