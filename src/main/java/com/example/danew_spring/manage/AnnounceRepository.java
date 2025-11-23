package com.example.danew_spring.manage;

import com.example.danew_spring.manage.domain.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, String> {
    // 날짜 기준 내림차순(최신순) 조회
    List<Announce> findAllByOrderByCreatedAtDesc();
}