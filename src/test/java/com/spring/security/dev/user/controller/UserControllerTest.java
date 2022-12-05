package com.spring.security.dev.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.dev.user.dao.dto.UserDto;
import com.spring.security.dev.user.dao.dto.UserJoinRequest;
import com.spring.security.dev.user.dao.dto.UserLoginRequest;
import com.spring.security.dev.user.exception.ErrorCode;
import com.spring.security.dev.user.exception.HospitalReviewAppException;
import com.spring.security.dev.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Autowired
    ObjectMapper objectMapper;

    private long expiredTimeMs = 1000 * 60 * 60; // 1 hour

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void joinUser_success() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("geun")
                .password("1234")
                .emailAddress("test@test.com")
                .build();

        when(userService.joinUser(any())).thenReturn(mock(UserDto.class));

        mockMvc.perform(post("/api/v1/users/join").with(csrf()) // test를 위해 mock객체에 csrf 인증 사용
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser
    void joinUser_fail() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("geun")
                .password("1234")
                .emailAddress("test@test.com")
                .build();

        when(userService.joinUser(any()))
                .thenThrow(new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Login 실패 - Id 없음")
    @WithMockUser
    void login_fail_id_not_found() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName("wrongUserName")
                .password("1234")
                .build();

        when(userService.login(any(), any()))
                .thenThrow(new HospitalReviewAppException(ErrorCode.NOT_FOUND, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Login 실패 - Password 틀림")
    @WithMockUser
    void login_fail_wrong_password() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName("wrongUserName")
                .password("1234")
                .build();

        when(userService.login(any(), any()))
                .thenThrow(new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login - 성공")
    @WithMockUser
    void login_success() throws Exception {

        String userName = "testName";
        String password = "testPwd";
        String token = userService.login(userName, password);
        System.out.println("token : " + token);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName(userName)
                .password(password)
                .build();

        when(userService.login(userName, password))
                .thenReturn(token);

        mockMvc.perform(post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}