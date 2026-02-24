package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.Component.JwtTokenProvider;
import com.bluespace.marine_mis_service.DTO.LoginDTO;
import com.bluespace.marine_mis_service.Repository.UserRepository;
import com.bluespace.marine_mis_service.Service.LoginService;
import com.bluespace.marine_mis_service.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <pre>
 * ===========================================================
 * Program Name : LoginServiceImpl
 * Description  : 로그인 관련 구현 서비스 API
 * Author       : 백두현
 * Create Date  : 2026-02-24
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-24  백두현  최초작성
 * ===========================================================
 * </pre>
 */

@Service("loginServiceImpl")
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public LoginDTO.Response login(LoginDTO.Request dto, HttpServletRequest request) {
        User user = userRepository.findByUsername(dto.getUsername()).orElse(null);

        // JWT 발급
        Map<String, Object> claims = Map.of("role", user.getRole().name());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name(), user.getUsername(),  claims);

        log.info("로그인 성공: email={}", user.getEmail());

        return LoginDTO.Response.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .name(user.getName())
                .accessToken(token)
                .lastLogin(LocalDateTime.now())
                .build();
    }
}
