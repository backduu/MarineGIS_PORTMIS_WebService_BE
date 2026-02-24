package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> searchByName(String name);
}
