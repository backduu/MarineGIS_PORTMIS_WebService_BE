package com.bluespace.marine_mis_service.Service;

import com.bluespace.marine_mis_service.DTO.CoastlineLocationDTO;
import com.bluespace.marine_mis_service.DTO.CoastlineRegionDTO;

import java.util.List;
import java.util.Optional;

public interface CoastlineService {
    List<CoastlineRegionDTO> getAllRegions();

    Optional<CoastlineLocationDTO> getLocation(String name);
}
