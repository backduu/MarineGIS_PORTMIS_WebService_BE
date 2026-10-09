package com.bluespace.marine_mis_service.Service;

import com.bluespace.marine_mis_service.DTO.RegisterDTO;

/**
 * <pre>
 * ===========================================================
 * Program Name : AuthService
 * Description  : 인증 관련 서비스 (회원가입 등)
 * Author       : 생성됨
 * Create Date  : 2026-03-07
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-03-07  생성  최초작성
 * ===========================================================
 * </pre>
 */
public interface AuthService {
    RegisterDTO.Response signup(RegisterDTO.Request request);
}
