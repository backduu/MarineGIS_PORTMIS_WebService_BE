package com.bluespace.marine_mis_service.Repository.impl;
import static com.bluespace.marine_mis_service.domain.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;

import com.bluespace.marine_mis_service.Repository.UserRepositoryCustom;
import com.bluespace.marine_mis_service.domain.entity.User;
import com.bluespace.marine_mis_service.domain.enums.UserStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * <pre>
 * ===========================================================
 * Program Name : UserRepositoryImpl
 * Description  : 사용자 레파지토리 구현 (Q클래스 주의)
 * Author       : 백두현
 * Create Date  : 2026-02-24
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-24  백두현  최초작성 (동적 쿼리용 메서드 구현)
 * ===========================================================
 * </pre>
 */

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> searchUsers(String name, String email, UserStatus status) {
        return queryFactory.selectFrom(user)
                .where(
                        nameEq(name),
                        emailContains(email),
                        statusEq(status)
                ).fetch();
    }

    // ***** 동적 쿼리용 조건절 메서드 구현 ***** //
    private BooleanExpression nameEq(String name) {
        return hasText(name) ? user.name.eq(name) : null;
    }

    private BooleanExpression emailContains(String email) {
        return hasText(email) ? user.email.containsIgnoreCase(email) : null;
    }

    private BooleanExpression statusEq(UserStatus status) {
        return status != null ? user.status.eq(status) : null;
    }
}
