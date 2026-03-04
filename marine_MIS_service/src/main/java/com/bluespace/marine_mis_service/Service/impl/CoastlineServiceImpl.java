package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.Repository.CoastlineRepository;
import com.bluespace.marine_mis_service.Service.CoastlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoastlineServiceImpl implements CoastlineService {

    private final CoastlineRepository coastlineRepository;

    @Override
    public List<String> getAllRegions() {
        return coastlineRepository.findDistinctSggNam();
    }
}
