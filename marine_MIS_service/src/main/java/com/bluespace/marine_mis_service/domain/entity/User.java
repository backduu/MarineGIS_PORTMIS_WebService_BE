package com.bluespace.marine_mis_service.domain.entity;

import com.bluespace.marine_mis_service.domain.enums.UserRole;
import com.bluespace.marine_mis_service.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Getter
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username; // id

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 11)
    private String phone;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = true, length = 50)
    private String nickname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = true, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
}
