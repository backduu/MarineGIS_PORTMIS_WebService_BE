package com.bluespace.marine_mis_service.Service;

import com.bluespace.marine_mis_service.DTO.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * ===========================================================
 * Program Name : LoginService
 * Description  : 로그인 관련 API
 * Author       : 백두현
 * Create Date  : 2026-02-24
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-24  백두현  최초작성
 * ===========================================================
 * </pre>
 */


public interface LoginService {
    LoginDTO.Response login(LoginDTO.Request dto, HttpServletRequest request);
}
