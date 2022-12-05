package com.spring.security.dev.user.dao;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),USER("user");

    private String role;
}
