package com.bluespace.marine_mis_service.DTO.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * 공공데이터 API 공통 응답 구조 DTO
 */
@Data
public class OdcloudApiResponse<T> {
    private int page;
    private int perPage;
    private int totalCount;
    private int currentCount;
    private int matchCount;
    private List<T> data;
}
