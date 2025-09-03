package com.example.danew_spring.bookmark;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.news.NewsService;
import com.example.danew_spring.bookmark.domain.Bookmark;
import com.example.danew_spring.news.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Bookmark>> saveBookmark(@RequestBody News newsRequest,
                                                              @RequestHeader("Authorization") String token) {
        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) 뉴스 저장 (이미 있으면 skip)
        newsRequest.setSavedAt(LocalDate.now());

        newsService.save(newsRequest);

        // 3) 북마크 생성 및 저장
        Bookmark bookmark = new Bookmark(userId, newsRequest.getId(), LocalDate.now());
        Bookmark savedBookmark = bookmarkService.save(bookmark);

        if(savedBookmark == null) {
            ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "북마크 저장 실패", null));
        }
        log.info("북마크 저장: {}", savedBookmark);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "북마크 저장 성공", savedBookmark));
    }

    @GetMapping(value = "/getBookmarks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<News>>> getBookmarks(
            @RequestHeader("Authorization") String token) {

        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        List<News> bookmarks = bookmarkService.getUserBookmarks(userId);
        log.info("북마크 뉴스 조회: {}", bookmarks);

        if(bookmarks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("error", "북마크 뉴스 조회 실패", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "북마크 뉴스 조회 성공", bookmarks));
    }

    @GetMapping(value = "/check-bookmark/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Boolean>> checkBookmark(
            @RequestHeader("Authorization") String token, @PathVariable String articleId) {
        try {
            String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

            Boolean isBookmark = bookmarkService.isBookmarked(userId, articleId);
            log.info("북마크 여부: {}", isBookmark);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", "북마크 여부 조회 성공", isBookmark));
        } catch (Exception e) {
            log.error("북마크 여부 조회 실패", e);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "북마크 여부 조회 실패", null));
        }
    }

        @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Boolean>> deleteBookmark(@PathVariable String id,
                                                  @RequestHeader("Authorization") String token) {
        try {
            String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            bookmarkService.deleteByUserIdAndArticleId(userId, id);
            log.info("북마크 삭제: {}", id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("success", "북마크 삭제 성공", true));
        } catch (Exception e) {
            log.error("북마크 삭제 실패: {}", id, e);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "북마크 삭제 실패", null));
        }
    }
}
