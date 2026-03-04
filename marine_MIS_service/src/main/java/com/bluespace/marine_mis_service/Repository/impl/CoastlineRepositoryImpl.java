package com.bluespace.marine_mis_service.Repository.impl;

import com.bluespace.marine_mis_service.Repository.CoastlineRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bluespace.marine_mis_service.domain.entity.QCoastline.coastline;

@RequiredArgsConstructor
public class CoastlineRepositoryImpl implements CoastlineRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findDistinctSggNam() {
        return queryFactory
                .select(coastline.sggNam)
                .distinct()
                .from(coastline)
                .where(coastline.sggNam.isNotNull())
                .orderBy(coastline.sggNam.asc())
                .fetch();
    }
}
