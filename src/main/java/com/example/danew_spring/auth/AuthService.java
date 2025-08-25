package com.example.danew_spring.auth;

import com.example.danew_spring.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    public User save(User user) {
        return authRepository.save(user);
    }

    public User findByUserId(String userId) {
        return authRepository.findByUserId(userId);
    }

    public boolean existsByUserId(String userId) {
        return authRepository.existsByUserId(userId);
    }

    public void delete(User user) {
        authRepository.delete(user);
    }

    public void deleteById(Long id) {
        authRepository.deleteById(id);
    }
}
