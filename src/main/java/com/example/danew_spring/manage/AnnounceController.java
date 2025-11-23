package com.example.danew_spring.manage;


import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.manage.domain.Announce;
import com.example.danew_spring.manage.dto.AnnounceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    // 1. 공지사항 저장 (관리자 기능)
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Announce>> saveAnnounce(
            @RequestBody AnnounceRequest announceRequest) {

        log.info("save Announce request content: {}", announceRequest.getContent());

        // 토큰 검증 (필요하다면 여기서 관리자 권한인지 체크하는 로직 추가)
        // String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // Announce 생성
        Announce announce = new Announce(
                UUID.randomUUID().toString(),   // ID 랜덤 생성
                announceRequest.getTitle(),
                announceRequest.getContent(),   // 내용
                LocalDate.now()                 // 생성일 (오늘 날짜)
        );

        Announce savedAnnounce = announceService.save(announce);

        if (savedAnnounce == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "공지사항 저장 실패", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "공지사항 저장 성공", savedAnnounce));
    }

    // 2. 공지사항 전체 목록 조회
    // 공지사항은 날짜별 검색보다는 '전체 목록'을 최신순으로 보는 경우가 많아 리스트 반환으로 구현했습니다.
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<Announce>>> getAnnounceList() {
        List<Announce> announceList = announceService.getAllAnnounces();

        log.info("Fetch Announce List size: {}", announceList.size());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "공지사항 목록 조회 성공", announceList));
    }

    // 3. 공지사항 삭제
    @DeleteMapping(value = "/delete/{announceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> deleteAnnounce(
            @PathVariable("announceId") String announceId) {

        // 여기서도 관리자 권한 체크가 필요할 수 있음

        try {
            announceService.deleteAnnounce(announceId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("success", "공지사항 삭제 성공", announceId));
        } catch (Exception e) {
            log.error("Delete Announce error", e);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "공지사항 삭제 실패", null));
        }
    }
}