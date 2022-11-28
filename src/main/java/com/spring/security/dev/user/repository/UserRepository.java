package com.spring.security.dev.user.repository;

import com.spring.security.dev.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // UserName으로 찾기
    Optional<User> findByUserName(String userName);
}
