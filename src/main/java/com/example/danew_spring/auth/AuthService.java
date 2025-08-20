package com.example.danew_spring.auth;

import com.example.danew_spring.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
