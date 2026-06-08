package com.example.demo.dto;

public class LoginResponseDto {

    private Long id;
    private String userId;
    private String username;
    private String email;

    public LoginResponseDto(Long id, String userId, String username, String email) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}