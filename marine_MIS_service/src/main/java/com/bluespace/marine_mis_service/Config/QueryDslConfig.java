package com.bluespace.marine_mis_service.Config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * ===========================================================
 * Program Name : QueryDslConfig
 * Description  : JPAQueryFactory를 빈으로 등록 위한 config 클래스
 * Author       : 백두현
 * Create Date  : 2026-02-23
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-23  백두현  최초작성
 * ===========================================================
 * </pre>
 */

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
