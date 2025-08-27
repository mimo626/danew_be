package com.example.danew_spring.diary;


import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.diary.domain.Diary;
import com.example.danew_spring.diary.dto.DiaryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DiaryRequest diaryRequest,
                                  @RequestHeader("Authorization") String token) {
        log.info("save Diary request: {}", diaryRequest);

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) Diary 생성
        Diary diary = new Diary(
                UUID.randomUUID().toString(),   // diaryId 랜덤 생성
                userId,                         // 토큰에서 추출한 userId
                LocalDate.now(),            // 오늘 날짜
                diaryRequest.getContent()       // 요청 본문에서 받은 content
        );

        // 3) 저장
        Diary savedDiary = diaryService.save(diary);

        return ResponseEntity.ok(savedDiary);
    }


    @GetMapping("/getByDate")
    public ResponseEntity<Diary> getByDate(
            @RequestHeader("Authorization") String token,
            @RequestParam String createdAt // "2025-08-28" 형식
    ) {
        LocalDate localDate = LocalDate.parse(createdAt);

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) 해당 유저의 날짜별 일기 조회
        Optional<Diary> optionalDiary = diaryService.findByUserIdAndCreatedAt(userId, localDate);

        // 3) 결과 반환
        return optionalDiary
                .map(ResponseEntity::ok)                 // 값 있으면 200 + Diary
                .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404
    }

}
