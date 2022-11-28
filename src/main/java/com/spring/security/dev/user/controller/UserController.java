package com.spring.security.dev.user.controller;

import com.spring.security.dev.user.domain.Response;
import com.spring.security.dev.user.domain.dto.UserDto;
import com.spring.security.dev.user.domain.dto.UserJoinRequest;
import com.spring.security.dev.user.domain.dto.UserJoinResponse;
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
}
