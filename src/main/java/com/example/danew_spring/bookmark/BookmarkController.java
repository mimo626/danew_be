package com.example.danew_spring.bookmark;

import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.news.NewsService;
import com.example.danew_spring.news.domain.Bookmark;
import com.example.danew_spring.news.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/save")
    public ResponseEntity<Bookmark> saveBookmark(@RequestBody News newsRequest,
                                                 @RequestHeader("Authorization") String token) {

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) 뉴스 저장 (이미 있으면 skip)
        newsRequest.setSavedAt(LocalDate.now());
        newsService.save(newsRequest);

        // 3) 북마크 생성 및 저장
        Bookmark bookmark = new Bookmark(userId, newsRequest.getArticleId(), LocalDate.now());
        Bookmark savedBookmark = bookmarkService.save(bookmark);

        return ResponseEntity.ok(savedBookmark);
    }

    @GetMapping("/getBookmarks")
    public ResponseEntity<List<News>> getBookmarks(
            @RequestHeader("Authorization") String token) {

        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        List<News> bookmarks = bookmarkService.getUserBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }
}
