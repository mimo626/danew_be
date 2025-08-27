package com.example.danew_spring.diary;


import com.example.danew_spring.diary.domain.Diary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("api/diary/create")
    public Diary save(@RequestBody Diary diary) {
        log.info("save Diary: {}", diary);
        Diary savedDiary = diaryService.save(diary);
        return savedDiary;
    }
}
