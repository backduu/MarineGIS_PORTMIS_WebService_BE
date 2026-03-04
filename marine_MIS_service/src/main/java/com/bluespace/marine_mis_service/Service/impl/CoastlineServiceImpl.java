package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.DTO.CoastlineLocationDTO;
import com.bluespace.marine_mis_service.DTO.CoastlineRegionDTO;
import com.bluespace.marine_mis_service.Repository.CoastlineRepository;
import com.bluespace.marine_mis_service.Service.CoastlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoastlineServiceImpl implements CoastlineService {

    private final CoastlineRepository coastlineRepository;

    @Override
    public List<CoastlineRegionDTO> getAllRegions() {
        return coastlineRepository.findDistinctSggNam();
    }

    @Override
    public Optional<CoastlineLocationDTO> getLocation(String sggNam) {
        return coastlineRepository.findLocationBySggNam(sggNam)
                .map(map -> new CoastlineLocationDTO(
                        (String) map.get("centerJson"),
                        (String) map.get("bbox")
                ));
    }
}
