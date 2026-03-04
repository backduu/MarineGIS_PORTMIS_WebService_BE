package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.DTO.CoastlineRegionDTO;

import java.util.List;

public interface CoastlineRepositoryCustom {
    List<CoastlineRegionDTO> findDistinctSggNam();
}
