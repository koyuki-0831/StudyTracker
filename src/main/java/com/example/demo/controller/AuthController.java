package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // アカウント登録
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @Valid @RequestBody RegisterRequestDto dto
    ) {
        LoginResponseDto response =
                authService.register(dto);

        return ResponseEntity.ok(response);
    }

    // ログイン
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto
    ) {
        LoginResponseDto response =
                authService.login(dto);

        return ResponseEntity.ok(response);
    }
}