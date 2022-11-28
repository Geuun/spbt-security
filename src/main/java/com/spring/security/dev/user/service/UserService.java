package com.spring.security.dev.user.service;

import com.spring.security.dev.user.domain.dto.UserDto;
import com.spring.security.dev.user.domain.dto.UserJoinRequest;
import com.spring.security.dev.user.domain.entity.User;
import com.spring.security.dev.user.exception.ErrorCode;
import com.spring.security.dev.user.exception.HospitalReviewAppException;
import com.spring.security.dev.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto joinUser(UserJoinRequest userJoinRequest) {
        /**
         * 비지니스 로직 - 회원가입
         * 회원 userName(id) 중복 Check
         * 중복이라면 예외처리 --> 회원가입 x
         */
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, userJoinRequest.getUserName());
                });

        User savedUser = userRepository.save(userJoinRequest.toEntity());
        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }
}
