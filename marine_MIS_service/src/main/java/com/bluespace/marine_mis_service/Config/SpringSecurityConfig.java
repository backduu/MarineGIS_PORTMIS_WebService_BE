package com.bluespace.marine_mis_service.Config;

import com.bluespace.marine_mis_service.Component.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 * ===========================================================
 * Program Name : SpringSecurity config file
 * Description  : Bean 등록 방식의 Security config 파일  (WebSecurityConfigurerAdapter 방식은 지양한다.)
 * Author       : 백두현
 * Create Date  : 2026-02-23
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-23  백두현  최초작성
 * 2026-02-23  백두현  bean 등록 방식, stateless 설정, CORS 설정 분리, Filter 순서 배치 정리
 * ===========================================================
 * </pre>
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SpringSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable) // basic authentication filter 비활성화
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않는 Stateless 방식으로 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Spring security의 필터체인은 순차적으로 검사하므로 아래 순서를 지켜야한다.
                        // 0. 구체적인 경로를 먼저 설정
                        .requestMatchers("/api/auth/change").authenticated()
                        // 1. 인증 없이 접근 가능한 경로
                        .requestMatchers("/welcome/**", "/api/user/signup", "/api/auth/login").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 2. 나머지 /api/user/** 는 인증 필요
                        .requestMatchers("/api/user/**").authenticated()
                        // 3. 에러 허용
                        .requestMatchers("/error").permitAll()
                        // 4. 그 외 모든 요청 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // spring security6 부턴 람다 스타일로 authenticationManager 설정
                .authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder));

        return http.build();
    }


    // 인증 관리자에 passwordEncoder를 setter에서 주입
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
