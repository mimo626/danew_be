package com.example.danew_spring.auth;

import com.example.danew_spring.auth.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/signup")
    public User save(@RequestBody User user) {
        log.info("signup: {}", user);
        user.setCreatedAt(LocalDateTime.now().toString());
        return authService.save(user);
    }
}

