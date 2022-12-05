package com.spring.security.dev.hospital.controller;

import com.spring.security.dev.hospital.dao.dto.ReviewCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @PostMapping
    public String write(@RequestBody ReviewCreateRequest reviewCreateRequest) {

        return "리뷰 등록에 성공했습니다.";
    }
}
