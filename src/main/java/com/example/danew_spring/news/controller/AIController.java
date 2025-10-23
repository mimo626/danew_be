package com.example.danew_spring.news.controller;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.news.domain.NewsSummary;
import com.example.danew_spring.news.repository.NewsSummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class AIController {
    private final ChatClient chatClient;
    @Autowired
    private NewsSummaryRepository newsSummaryRepository;

    public AIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/news/summarize")
    public ResponseEntity<ApiResponse<String>> getSummarizeNews(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        String articleId = request.get("articleId");
        log.info("뉴스 : {}", content);

        // DB에서 이미 요약 존재하는지 확인
        Optional<NewsSummary> cached = newsSummaryRepository.findByArticleId(articleId);
        if (cached.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>("success", "캐시에서 반환", cached.get().getSummary()));
        }
        String prompt = content + "\n\n이 내용의 핵심을 파악해서 4줄 이내로 요약해줘. 부가적인 말을 필요없고, 요약한 내용만 반환해줘";
        String summary = chatClient.prompt().user(prompt).call().content();

        if (summary == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "뉴스 요약 실패", null));
        }

        // DB에 저장
        NewsSummary entity = new NewsSummary();
        entity.setArticleId(articleId);
        entity.setSummary(summary);
        newsSummaryRepository.save(entity);

        log.info("AI 요역: {}", summary);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "뉴스 요약 성공", summary));
    }

}
