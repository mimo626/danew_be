package com.example.danew_spring.auth;

import com.example.danew_spring.ApiResponse;
import com.example.danew_spring.JwtTokenProvider;
import com.example.danew_spring.auth.domain.User;
import com.example.danew_spring.auth.dto.LoginRequest;
import com.example.danew_spring.auth.dto.UpdateUserRequest;
import com.example.danew_spring.auth.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping(value = "/api/auth/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    // produces는 서버가 클라이언트에 어떤 형식으로 응답할지 지정
    // JSON 응답을 보낼 때는 항상 produces = MediaType.APPLICATION_JSON_VALUE 권장
    public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody User user) {
        log.info("signup: {}", user);
        user.setCreatedAt(LocalDateTime.now().toString());

        User savedUser = authService.save(user);

        if (savedUser == null) {
            return ResponseEntity.ok(new ApiResponse<>("error", "이미 존재하는 아이디입니다.", null));
        }

        UserResponse userResponse = new UserResponse(savedUser.getUserId(), null, savedUser.getCreatedAt());
        return ResponseEntity.ok(new ApiResponse<>("success", "회원가입 성공", userResponse));
    }

    // 로그인
    @PostMapping(value = "/api/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
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

        UserResponse userResponse = new UserResponse(user.getUserId(), token, user.getCreatedAt());


        return ResponseEntity.ok(new ApiResponse<>("success", "로그인 성공", userResponse));
    }


    @GetMapping(value = "/api/auth/check-username", produces = MediaType.APPLICATION_JSON_VALUE)
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


    @GetMapping(value = "/api/auth/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PatchMapping(value = "/api/auth/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserRequest request) {

        // 1) 토큰에서 userId 추출
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));

        // 2) 유저 조회
        User user = authService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.ok(new ApiResponse<>("error", "유저를 찾을 수 없습니다.", null));
        }

        // 3) 수정할 값만 반영
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        if (request.getGender() != null && !request.getGender().isBlank()) {
            user.setGender(request.getGender());
        }

        // 4) DB 저장
        User updatedUser = authService.save(user);

        log.info("유저 수정: {}", updatedUser);

        return ResponseEntity.ok(new ApiResponse<>("success", "유저 정보 수정 성공", updatedUser));
    }

    // 회원탈퇴
    @DeleteMapping(value = "/api/auth/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @RequestHeader("Authorization") String token) {

        try {
            // 1) 토큰에서 userId 추출
            String jwt = token.replace("Bearer ", "");
            String userId = jwtTokenProvider.getUserIdFromToken(jwt);

            // 2) 유저 조회
            User user = authService.findByUserId(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>("error", "존재하지 않는 사용자입니다.", null));
            }

            // 3) DB에서 유저 삭제
            authService.deleteUser(userId);

            // 4) RefreshToken 같은 게 있다면 함께 삭제 (옵션)

            return ResponseEntity.ok(new ApiResponse<>("success", "회원탈퇴가 완료되었습니다.", null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "회원탈퇴 처리 중 오류가 발생했습니다.", null));
        }
    }

}

