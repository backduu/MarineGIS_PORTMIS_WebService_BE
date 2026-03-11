package com.bluespace.marine_mis_service.Controller;

import com.bluespace.marine_mis_service.Service.ObservatoryService;
import com.bluespace.marine_mis_service.domain.entity.ObsLocations;
import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <pre>
 * ===========================================================
 * Program Name : ObservatoryController
 * Description  : 조위관측소에서 실측한 데이터들 관련 API
 * Author       : 백두현
 * Create Date  : 2026-03-10
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-03-10  백두현  최초작성
 * ===========================================================
 * </pre>
 */

@RestController
@RequestMapping("/api/observatory")
@RequiredArgsConstructor
public class ObservatoryController {
    private final ObservatoryService observatoryService;

    @GetMapping("/water-temp")
    public List<WaterTemp> getWaterTemp(
            @RequestParam(name = "obsCode", defaultValue = "DT_0001") String obsCode,
            @RequestParam(name = "reqDate", required = false) String reqDate
    ) {
        if (reqDate == null || reqDate.isEmpty()) {
            reqDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        
        return observatoryService.fetchAndSaveWaterTemp(obsCode, reqDate);
    }

    @GetMapping("/location")
    public List<ObsLocations> getObsLocations(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size") Integer size
    ) {

        return observatoryService.fetchAndSaveObservationLocation(page, size);
    }
}
