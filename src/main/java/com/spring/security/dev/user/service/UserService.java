package com.spring.security.dev.user.service;

import com.spring.security.dev.user.dao.dto.UserDto;
import com.spring.security.dev.user.dao.dto.UserJoinRequest;
import com.spring.security.dev.user.dao.entity.User;
import com.spring.security.dev.user.exception.ErrorCode;
import com.spring.security.dev.user.exception.HospitalReviewAppException;
import com.spring.security.dev.user.repository.UserRepository;
import com.spring.security.dev.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; // 1 hour


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

        User savedUser = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.NOT_FOUND, "해당 유저가 존재하지 않습니다."));
    }

    public String login(String  userName, String  password) {
        /**
         * userName이 있는지 여부 확인
         * 없다면 NOT FOUND Error 발생
         */
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.NOT_FOUND, String.format("User %s was not found.", userName)));

        // Password 일치 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, "The password is wrong.");
        }

        // Token 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);

        return token;
    }
}
