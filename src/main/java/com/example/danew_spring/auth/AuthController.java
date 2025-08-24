package com.example.danew_spring.auth;

import com.example.danew_spring.auth.domain.User;
import com.example.danew_spring.auth.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    // 회원가입
    @PostMapping("/api/auth/signup")
    public User save(@RequestBody User user) {
        log.info("signup: {}", user);
        user.setCreatedAt(LocalDateTime.now().toString());
        return authService.save(user);
    }

    // 로그인
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("login: {}", loginRequest);

        User user = authService.findByUserId(loginRequest.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("존재하지 않는 아이디입니다.");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok("success");
    }

    // 아이디 중복 확인
    @GetMapping("/api/auth/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String userId) {
        boolean exists = authService.existsByUserId(userId);
        log.info("유저 아이디 중복체크: {}", exists);

        if (exists) {
            // 이미 사용 중인 아이디 -> false
            return ResponseEntity.ok(false);
        }
        // 사용 가능한 아이디 -> true
        return ResponseEntity.ok(true);
    }
}

