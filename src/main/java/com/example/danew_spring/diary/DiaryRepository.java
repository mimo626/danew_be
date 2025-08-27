package com.example.danew_spring.diary;

import com.example.danew_spring.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDiaryId(String diaryId);

    void deleteByDiaryId(String diaryId);
}
