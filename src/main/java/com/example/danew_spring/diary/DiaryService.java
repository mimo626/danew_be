package com.example.danew_spring.diary;

import com.example.danew_spring.diary.domain.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }
    public Diary findByDiaryId(String diaryId) {
        return diaryRepository.findByDiaryId(diaryId);
    }

    public void deleteByDiaryId(String diaryId) {
        diaryRepository.deleteByDiaryId(diaryId);
    }
}
