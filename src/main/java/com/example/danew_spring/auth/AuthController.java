package com.example.danew_spring.auth;

import com.example.danew_spring.auth.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/login")
    public User save(@RequestBody User user) {
        log.info(user.toString());
        return authService.save(user);
    }
}

