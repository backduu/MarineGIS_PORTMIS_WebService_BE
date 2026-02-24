package com.bluespace.marine_mis_service.Controller;

import com.bluespace.marine_mis_service.DTO.LoginDTO;
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
 * Description  : 로그인 API
 * Author       : 백두현
 * Create Date  : 2026-02-24
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-24  백두현  최초작성
 * ===========================================================
 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {
    private final LoginService loginService;


    @PostMapping("/login")
    public LoginDTO.Response login(@Valid @RequestBody LoginDTO.Request dto, HttpServletRequest request) {
        return loginService.login(dto, request);
    }
}
