package com.bluespace.marine_mis_service.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LoginDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @Size(min = 3, max = 50, message="[DTO] 아이디는 3 ~ 50자 사이어야 합니다.")
        private String username;
        @Size(min = 8, max = 150, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String accessToken;
        private String refreshToken;
        private String role;
        private String username;
        private String name;

        private LocalDateTime lastLogin;
    }
}
