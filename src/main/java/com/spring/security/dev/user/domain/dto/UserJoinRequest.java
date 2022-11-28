package com.spring.security.dev.user.domain.dto;

import com.spring.security.dev.user.domain.entity.User;
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
    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .emailAddress(this.emailAddress)
                .build();
    }
}
