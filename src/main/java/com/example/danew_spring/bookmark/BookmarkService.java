package com.example.danew_spring.bookmark;

import com.example.danew_spring.bookmark.domain.BookmarkId;
import com.example.danew_spring.news.NewsRepository;
import com.example.danew_spring.bookmark.domain.Bookmark;
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
    public void deleteByUserIdAndArticleId(String userId, String articleId) {
        // 복합키 객체 생성
        BookmarkId bookmarkId = new BookmarkId(userId, articleId);
        bookmarkRepository.deleteById(bookmarkId);
    }

    public Boolean isBookmarked(String userId, String articleId) {
        BookmarkId bookmarkId = new BookmarkId(userId, articleId);
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId);

        if(bookmark == null){
            return false;
        }else {
            return true;
        }
    }

    public List<News> getUserBookmarks(String userId) {
        // userId로 북마크 조회
        List<Bookmark> bookmarks = bookmarkRepository.findByIdUserId(userId);

        // articleId 추출
        List<String> articleIds = bookmarks.stream()
                .map(bookmark -> bookmark.getId().getArticleId()) // BookmarkId에서 articleId
                .toList();

        // 뉴스 조회
        List<News> newsList = newsRepository.findByIdIn(articleIds);

        return newsList;
    }

}
