package com.bluespace.marine_mis_service.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN_ROLE("관리자 권한"),
    USER_ROLE("사용자권한");

    private final String description;
}
