package com.example.danew_spring.diary;

import com.example.danew_spring.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByDiaryId(String diaryId);

    void deleteByDiaryId(String diaryId);

    Optional<Diary> findByUserIdAndCreatedAt(String userId, LocalDate createdAt);

}
