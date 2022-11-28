package com.spring.security.dev.user.service;

import com.spring.security.dev.user.domain.dto.UserDto;
import com.spring.security.dev.user.domain.dto.UserJoinRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto joinUser(UserJoinRequest userJoinRequest) {

        return new UserDto();
    }
}
