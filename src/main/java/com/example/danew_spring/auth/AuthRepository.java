package com.example.danew_spring.auth;


import com.example.danew_spring.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    boolean existsByUserId(String userId);

    void deleteByUserId(String userId);
}

