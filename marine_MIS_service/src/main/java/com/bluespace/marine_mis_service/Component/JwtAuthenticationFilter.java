package com.bluespace.marine_mis_service.Component;

import com.bluespace.marine_mis_service.Service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <pre>
 * ===========================================================
 * Program Name : JwtAuthenticationFilter
 * Description  : JWT 인증 필터 전용 파일
 * Author       : 백두현
 * Create Date  : 2026-02-23
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-23  백두현  최초작성
 * ===========================================================
 * </pre>
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        String username = null;
        String email = null;
        String role = null;

        log.debug("Authorization header: {}", authHeader);

        if(authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            log.debug("Bearer Token: {}", token);

            email = jwtTokenProvider.getEmailFromToken(token);
            username = jwtTokenProvider.getUsernameFromToken(token);
            role = jwtTokenProvider.getRoleFromToken(token);
        }


        // 토큰이 유효하다면 사용자 정보를 꺼내어 Spring Security의 SecurityContext에 인증 객체를 심어준다.
        if(username != null && email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtTokenProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                log.debug(">>> authToken {}",  authToken);


                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
