package com.bluespace.marine_mis_service.Controller;

import com.bluespace.marine_mis_service.Repository.UserRepository;
import com.bluespace.marine_mis_service.domain.entity.User;
import com.bluespace.marine_mis_service.domain.enums.UserRole;
import com.bluespace.marine_mis_service.domain.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
// 테스트 용 컨트롤러
@RestController
@RequestMapping("/welcome")
@RequiredArgsConstructor
public class WelcomeController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to Marine MIS Service";
    }

    @GetMapping("/getMembers")
    public List<User> getMembers() {
        // 임시 데이터 저장 (테스트할 때마다 중복 생성될 수 있으니 주의)
        User testUser = User.builder()
                .username("test_user_" + System.currentTimeMillis()) // 중복 방지
                .name("백두현")
                .password("1234")
                .email("test@test.com")
                .phone("01012345678")
                .role(UserRole.USER_ROLE)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(testUser);

        // QueryDSL을 사용한 조회 (또는 기본 findAll)
        return userRepository.searchUsers(null, null, null);
    }
}
