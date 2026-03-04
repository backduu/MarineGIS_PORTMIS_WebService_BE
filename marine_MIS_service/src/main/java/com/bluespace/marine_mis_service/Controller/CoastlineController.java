package com.bluespace.marine_mis_service.Controller;

import com.bluespace.marine_mis_service.DTO.CoastlineRegionDTO;
import com.bluespace.marine_mis_service.DTO.CoastlineLocationDTO;
import com.bluespace.marine_mis_service.Service.CoastlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coastline")
@RequiredArgsConstructor
public class CoastlineController {

    private final CoastlineService coastlineService;

    /**
     * 해안선 지역(시군구명) 목록 조회
     * all_countries_coastline_2025 테이블의 sgg_nam 컬럼을 중복 없이 조회한다.
     *
     * @return 시군구명 리스트 (DTO)
     */
    @GetMapping("/regions")
    public ResponseEntity<List<CoastlineRegionDTO>> getCoastlineRegions() {
        List<CoastlineRegionDTO> regions = coastlineService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    /**
     * 특정 지역의 위치 정보(중심점, BBOX) 조회
     *
     * @param sggNam 시군구명
     * @return 위치 정보 (DTO)
     */
    @GetMapping("/location")
    public ResponseEntity<CoastlineLocationDTO> getRegionLocation(@RequestParam String sggNam) {
        return coastlineService.getLocation(sggNam)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
