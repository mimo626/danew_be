package com.example.danew_spring.news;

import com.example.danew_spring.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class AIController {
    private final ChatClient chatClient;

    public AIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/news/summarize")
    public ResponseEntity<ApiResponse<String>> getSummarizeNews(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String content = chatClient.prompt()
                .user(prompt + "\n\n이 내용의 핵심을 파악해서 4줄 이내로 요약해줘. 부가적인 말을 필요없고, 요약한 내용만 반환해줘")
                .call()
                .content();

        if (content == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "뉴스 요약 실패", null));
        }

        log.info("AI 요역: {}", content);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "뉴스 요약 성공", content));
    }

}
