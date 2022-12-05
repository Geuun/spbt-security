package com.spring.security.dev.user.dao.dto;

import com.spring.security.dev.user.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
    private String emailAddress;

    // Request -> Entity
    public User toEntity(String password) {
        return User.builder()
                .userName(this.userName)
                .password(password)
                .emailAddress(this.emailAddress)
                .build();
    }
}
