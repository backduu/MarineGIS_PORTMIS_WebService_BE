package com.bluespace.marine_mis_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RegisterDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 3, max = 50, message="[DTO] 아이디는 3 ~ 50자 사이어야 합니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 150, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
        private String name;

        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(max = 11, message = "전화번호는 11자 이하여야 합니다.")
        private String phone;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력하세요.")
        @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
        private String email;

        @Size(max = 50, message = "닉네임은 50자 이하여야 합니다.")
        private String nickname;

        @Size(max = 255, message = "주소는 255자 이하여야 합니다.")
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String username;
        private String name;
        private String email;
        private String phone;
        private String nickname;
        private String address;
        private String role;
        private String status;
        private LocalDateTime created;
    }
}
