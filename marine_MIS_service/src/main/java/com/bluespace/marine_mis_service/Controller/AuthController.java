package com.bluespace.marine_mis_service.Controller;

import com.bluespace.marine_mis_service.DTO.LoginDTO;
import com.bluespace.marine_mis_service.DTO.RegisterDTO;
import com.bluespace.marine_mis_service.Service.AuthService;
import com.bluespace.marine_mis_service.Service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * ===========================================================
 * Program Name : authController
 * Description  : 로그인/회원가입 API
 * Author       : 백두현
 * Create Date  : 2026-02-24
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-24  백두현  최초작성
 * 2026-03-07  생성   회원가입 API 추가
 * ===========================================================
 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {
    private final LoginService loginService;
    private final AuthService authService;

    @PostMapping("/login")
    public LoginDTO.Response login(@Valid @RequestBody LoginDTO.Request dto, HttpServletRequest request) {
        return loginService.login(dto, request);
    }

    @PostMapping("/signup")
    public RegisterDTO.Response signup(@Valid @RequestBody RegisterDTO.Request request) {
        return authService.signup(request);
    }
}
