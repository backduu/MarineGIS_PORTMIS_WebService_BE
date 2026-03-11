package com.bluespace.marine_mis_service.Repository.impl;

import com.bluespace.marine_mis_service.Repository.WaterTempRepositoryCustom;
import com.bluespace.marine_mis_service.domain.entity.QWaterTemp;
import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WaterTempRepositoryImpl implements WaterTempRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WaterTemp> findWaterTempByConditions(String obsCode, String startDate, String endDate) {
        QWaterTemp waterTemp = QWaterTemp.waterTemp;

        return queryFactory.selectFrom(waterTemp)
                .where(
                        obsCodeEq(obsCode),
                        obsrvnDtBetween(startDate, endDate)
                )
                .orderBy(waterTemp.obsrvnDt.desc())
                .fetch();
    }

    private BooleanExpression obsCodeEq(String obsCode) {
        return obsCode != null ? QWaterTemp.waterTemp.obsCode.eq(obsCode) : null;
    }

    private BooleanExpression obsrvnDtBetween(String startDate, String endDate) {
        if (startDate != null && endDate != null) {
            return QWaterTemp.waterTemp.obsrvnDt.between(startDate, endDate);
        } else if (startDate != null) {
            return QWaterTemp.waterTemp.obsrvnDt.goe(startDate);
        } else if (endDate != null) {
            return QWaterTemp.waterTemp.obsrvnDt.loe(endDate);
        }
        return null;
    }
}
