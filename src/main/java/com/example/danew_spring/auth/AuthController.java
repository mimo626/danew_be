package com.example.danew_spring.auth;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.auth.domain.User;
import com.example.danew_spring.auth.dto.LoginRequest;
import com.example.danew_spring.auth.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody User user) {
        log.info("signup: {}", user);
        user.setCreatedAt(LocalDateTime.now().toString());

        User savedUser = authService.save(user);

        if (savedUser == null) {
            return ResponseEntity.ok(new ApiResponse<>("error", "이미 존재하는 아이디입니다.", null));
        }

        UserResponse userResponse = new UserResponse(savedUser.getUserId(), savedUser.getCreatedAt());
        return ResponseEntity.ok(new ApiResponse<>("success", "회원가입 성공", userResponse));
    }

    // 로그인
    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest loginRequest) {
        log.info("login: {}", loginRequest);

        User user = authService.findByUserId(loginRequest.getUserId());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "존재하지 않는 아이디입니다.", null));
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("error", "비밀번호가 일치하지 않습니다.", null));
        }

        // JWT 토큰 발급
        String token = jwtTokenProvider.generateToken(user.getUserId());

        return ResponseEntity.ok(new ApiResponse<>("success", "로그인 성공", null));
    }


    @GetMapping("/api/auth/check-username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(@RequestParam String userId) {
        boolean exists = authService.existsByUserId(userId);
        log.info("유저 아이디 중복체크: {}", exists);

        if (exists) {
            // 이미 사용 중인 아이디 -> status = "error", data = false
            return ResponseEntity.ok(new ApiResponse<>("error", "이미 사용 중인 아이디입니다.", false));
        }
        // 사용 가능한 아이디 -> status = "success", data = true
        return ResponseEntity.ok(new ApiResponse<>("success", "사용 가능한 아이디입니다.", true));
    }


    @GetMapping("/api/auth/getUser")
    public ResponseEntity<ApiResponse<User>> getUserInfo(@RequestHeader("Authorization") String token) {

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) DB에서 유저 정보 조회
        User user = authService.findByUserId(userId);

        if (user == null) {
            return ResponseEntity.ok(new ApiResponse<>("error", "유저를 찾을 수 없습니다.", null));
        }

        log.info("유저 조회: {}", user);

        return ResponseEntity.ok(new ApiResponse<>("success", "유저 정보 조회 성공", user));
    }

}

