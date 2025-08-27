package com.example.danew_spring.diary;


import com.example.danew_spring.diary.domain.Diary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Diary diary) {
        log.info("save Diary: {}", diary);
        Diary savedDiary = diaryService.save(diary);
        return ResponseEntity.ok(savedDiary);
    }

    @GetMapping("/getByUserIdAndDate")
    public ResponseEntity<Diary> getByUserIdAndDate(
            @RequestParam String userId,
            @RequestParam String createdAt // "2025-08-28" 형식
    ) {
        LocalDate localDate = LocalDate.parse(createdAt);

        Optional<Diary> optionalDiary = diaryService.findByUserIdAndCreatedAt(userId, localDate);

        return optionalDiary
            .map(ResponseEntity::ok)                 // 값 있으면 200 + Diary
                .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404
    }
}
