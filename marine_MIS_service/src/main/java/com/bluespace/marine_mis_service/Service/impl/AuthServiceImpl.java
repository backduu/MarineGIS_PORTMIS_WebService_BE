package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.DTO.RegisterDTO;
import com.bluespace.marine_mis_service.Repository.UserRepository;
import com.bluespace.marine_mis_service.Service.AuthService;
import com.bluespace.marine_mis_service.domain.entity.User;
import com.bluespace.marine_mis_service.domain.enums.UserRole;
import com.bluespace.marine_mis_service.domain.enums.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <pre>
 * ===========================================================
 * Program Name : AuthServiceImpl
 * Description  : 회원가입 구현 서비스
 * Author       : 생성됨
 * Create Date  : 2026-03-07
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-03-07  생성  최초작성
 * ===========================================================
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegisterDTO.Response signup(RegisterDTO.Request request) {
        // Duplication checks
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create User entity with defaults
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .name(request.getName())
                .phone(request.getPhone())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .address(request.getAddress())
                .role(UserRole.USER_ROLE)
                .status(UserStatus.ACTIVE)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        return RegisterDTO.Response.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .nickname(saved.getNickname())
                .address(saved.getAddress())
                .role(saved.getRole().name())
                .status(saved.getStatus().name())
                .created(saved.getCreated())
                .build();
    }
}
