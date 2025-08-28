package com.example.danew_spring.diary;


import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.diary.domain.Diary;
import com.example.danew_spring.diary.dto.DiaryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Diary> save(@RequestBody DiaryRequest diaryRequest,
                                  @RequestHeader("Authorization") String token) {
        log.info("save Diary request: {}", diaryRequest.getCreatedAt());

        LocalDate localDate = LocalDate.parse(diaryRequest.getCreatedAt());
                // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) Diary 생성
        Diary diary = new Diary(
                UUID.randomUUID().toString(),   // diaryId 랜덤 생성
                userId,                         // 토큰에서 추출한 userId
                localDate,            // 오늘 날짜
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

        log.info("Diary getByDate: {}", optionalDiary);

        // 3) 결과 반환: 값이 없으면 body를 null로 보내서 200 OK 유지
        return ResponseEntity.ok(optionalDiary.orElse(new Diary()));
    }

    @PutMapping("/update/{diaryId}")
    public ResponseEntity<Diary> update(
            @PathVariable("diaryId") String diaryId,
            @RequestBody DiaryRequest diaryRequest,
            @RequestHeader("Authorization") String token) {

        log.info("update Diary request: {}, content: {}", diaryRequest.getCreatedAt(), diaryRequest.getContent());

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) 기존 다이어리 조회
        Diary existingDiary = diaryService.findByDiaryId(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found with id: " + diaryId));

        // 3) 사용자 검증 (권한 체크)
        if (!existingDiary.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 4) 다이어리 내용 업데이트
        existingDiary.setContent(diaryRequest.getContent());

        // 5) 저장
        Diary updatedDiary = diaryService.save(existingDiary);

        return ResponseEntity.ok(updatedDiary);
    }


}
