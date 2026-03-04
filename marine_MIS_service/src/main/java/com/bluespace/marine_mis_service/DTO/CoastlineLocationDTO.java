package com.bluespace.marine_mis_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 해안선 위치 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoastlineLocationDTO {
    private String centerJson; // GeoJSON 형태의 중심점
    private String bbox;       // BOX(x min, y min, x max, y max) 형태
}
