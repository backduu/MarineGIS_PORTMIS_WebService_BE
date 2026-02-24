package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.User;
import com.bluespace.marine_mis_service.domain.enums.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {
    List<User> searchUsers(String name, String email, UserStatus status);
}
