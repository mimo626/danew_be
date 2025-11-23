package com.example.danew_spring.manage;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.manage.domain.Question;
import com.example.danew_spring.manage.dto.QuestionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // 1. 자주 묻는 질문 저장
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Question>> saveQuestion(
            @RequestBody QuestionRequest questionRequest) {

        log.info("save Question: {}", questionRequest.getQuestion());

        // Question 생성
        Question question = new Question(
                UUID.randomUUID().toString(),   // ID 랜덤 생성
                questionRequest.getQuestion(),  // 질문
                questionRequest.getAnswer()     // 답변
        );

        Question savedQuestion = questionService.save(question);

        if (savedQuestion == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "FAQ 저장 실패", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "FAQ 저장 성공", savedQuestion));
    }

    // 2. 자주 묻는 질문 전체 목록 조회
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<Question>>> getQuestionList() {
        List<Question> questionList = questionService.getAllQuestions();

        log.info("Fetch Question List size: {}", questionList.size());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "FAQ 목록 조회 성공", questionList));
    }

    // 3. 자주 묻는 질문 삭제
    @DeleteMapping(value = "/delete/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> deleteQuestion(
            @PathVariable("questionId") String questionId) {

        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("success", "FAQ 삭제 성공", questionId));
        } catch (Exception e) {
            log.error("Delete Question error", e);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "FAQ 삭제 실패", null));
        }
    }
}