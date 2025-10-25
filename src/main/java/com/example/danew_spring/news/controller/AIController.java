package com.example.danew_spring.news.controller;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.news.domain.NewsSummary;
import com.example.danew_spring.news.repository.NewsSummaryRepository;
import lombok.extern.slf4j.Slf4j;
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

        String prompt = content +
                "\n" +
                "---\n" +
                "당신은 베테랑 뉴스 분석가입니다. 위 기사 서론만이 아닌 본문 전체의 내용을 바탕으로, " +
                "독자가 반드시 알아야 할 핵심 사실(누가, 무엇을, 언제, 어디서, 왜)을 중심으로 4문장 이내로 요약해 주세요." +
                "다른 문장 없이 뉴스 요약 내용만 반환해 주세요.\n";
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

        log.info("AI 요약: {}", summary);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "뉴스 요약 성공", summary));
    }

}
