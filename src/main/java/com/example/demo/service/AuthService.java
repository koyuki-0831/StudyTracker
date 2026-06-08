package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public LoginResponseDto register(RegisterRequestDto dto) {

        if (userRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalArgumentException("このユーザーIDはすでに使用されています");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("このメールアドレスはすでに使用されています");
        }

        UserModel user = new UserModel();

        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 現段階では平文。あとで必ずハッシュ化する
        user.setPassword(dto.getPassword());

        UserModel saved = userRepository.save(user);

        return toLoginResponseDto(saved);
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        UserModel user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("メールアドレスまたはパスワードが違います")
                );

        // 現段階では平文比較。あとで必ずハッシュ化する
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("メールアドレスまたはパスワードが違います");
        }

        return toLoginResponseDto(user);
    }

    private LoginResponseDto toLoginResponseDto(UserModel user) {

        return new LoginResponseDto(
                user.getId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
