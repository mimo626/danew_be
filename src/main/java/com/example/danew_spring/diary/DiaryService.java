package com.example.danew_spring.diary;

import com.example.danew_spring.diary.domain.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }

    public Optional<Diary> findByDiaryId(String diaryId) {
        return diaryRepository.findByDiaryId(diaryId);
    }

    public Optional<Diary> findByUserIdAndCreatedAt(String userId, LocalDate createdAt){
        return diaryRepository.findByUserIdAndCreatedAt(userId, createdAt);
    }

    public void deleteByDiaryId(String diaryId) {
        diaryRepository.deleteByDiaryId(diaryId);
    }
}
