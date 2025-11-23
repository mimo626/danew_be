package com.example.danew_spring.manage;


import com.example.danew_spring.manage.domain.Announce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    private final AnnounceRepository announceRepository;

    // 공지사항 저장
    @Transactional
    public Announce save(Announce announce) {
        return announceRepository.save(announce);
    }

    // 공지사항 전체 조회 (최신순)
    @Transactional(readOnly = true)
    public List<Announce> getAllAnnounces() {
        return announceRepository.findAllByOrderByCreatedAtDesc();
    }

    // 공지사항 삭제 (필요 시 사용)
    @Transactional
    public void deleteAnnounce(String announceId) {
        announceRepository.deleteById(announceId);
    }
}