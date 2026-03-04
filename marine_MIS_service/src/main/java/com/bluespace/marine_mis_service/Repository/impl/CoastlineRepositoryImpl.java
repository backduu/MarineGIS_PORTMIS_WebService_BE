package com.bluespace.marine_mis_service.Repository.impl;

import com.bluespace.marine_mis_service.DTO.CoastlineRegionDTO;
import com.bluespace.marine_mis_service.Repository.CoastlineRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bluespace.marine_mis_service.domain.entity.QCoastline.coastline;

@RequiredArgsConstructor
public class CoastlineRepositoryImpl implements CoastlineRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CoastlineRegionDTO> findDistinctSggNam() {
        return queryFactory
                .select(Projections.constructor(CoastlineRegionDTO.class,
                        coastline.id.min(),
                        coastline.sggNam))
                .from(coastline)
                .where(coastline.sggNam.isNotNull())
                .groupBy(coastline.sggNam)
                .orderBy(coastline.sggNam.asc())
                .fetch();
    }
}
