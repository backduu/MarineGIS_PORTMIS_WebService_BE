package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * ===========================================================
 * Program Name : WelcomeController
 * Description  : 환영/헬스 체크 API
 * Author       : <YOUR_NAME>
 * Create Date  : 2026-02-11
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-11  <YOUR_NAME>  최초작성
 * ===========================================================
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID를 가진 사용자를 찾을 수 없습니다."));
    }
}
