package com.spring.security.dev.user.controller;

import com.spring.security.dev.user.dao.Response;
import com.spring.security.dev.user.dao.dto.*;
import com.spring.security.dev.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Data PipeLine>
 * Client → Request → Controller → Service → Dto → Response<T> → Response → Client
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.joinUser(userJoinRequest);
        UserJoinResponse userJoinResponse = new UserJoinResponse(userDto.getUserName(), userDto.getEmailAddress());
        return Response.success(userJoinResponse);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
