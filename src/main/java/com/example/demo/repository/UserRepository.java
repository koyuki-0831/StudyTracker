package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	
	//userIdでユーザー取得
    Optional<UserModel> findByUserId(String userId);
    
    //ログイン時にemailでユーザー取得
    Optional<UserModel> findByEmail(String email);
    
    //userId重複チェック
    boolean existsByUserId(String userId);
    
    //email重複チェック
    boolean existsByEmail(String email);
}