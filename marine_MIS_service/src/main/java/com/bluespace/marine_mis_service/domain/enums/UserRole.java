package com.bluespace.marine_mis_service.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("관리자 권한"),
    GROUP_ADMIN("그룹 관리자 권한"),
    ROLE_USER("사용자권한"),
    ROLE_ANONYMOUS("익명 사용자 권한"),
    ROLE_AUTHENTICATED("인증 사용자 권한");

    private final String description;
}
