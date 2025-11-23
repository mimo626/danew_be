package com.example.danew_spring.manage;

import com.example.danew_spring.manage.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 저장
    @Transactional
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    // 질문 전체 조회
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // 질문 삭제
    @Transactional
    public void deleteQuestion(String questionId) {
        questionRepository.deleteById(questionId);
    }
}