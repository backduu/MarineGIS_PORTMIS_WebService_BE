package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import java.util.List;

public interface WaterTempRepositoryCustom {
    List<WaterTemp> findWaterTempByConditions(String obsCode, String startDate, String endDate);
}
