package com.bluespace.marine_mis_service.DTO.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * uddi:184c0fa3-c7a6-453e-9853-c10e2c756309 (20250731) API 모델
 */
@Data
public class VesselControlModel2025 {
    @JsonProperty("관제항구")
    private String portName;
    
    @JsonProperty("호출부호")
    private String callSign;
    
    @JsonProperty("관제입항년도")
    private Integer controlEntryYear;
    
    @JsonProperty("관제입항횟수")
    private Integer controlEntryCount;
    
    @JsonProperty("선박신고항구")
    private String reportPortName;
    
    @JsonProperty("신고입항년도")
    private Integer reportEntryYear;
    
    @JsonProperty("신고입항횟수")
    private Integer reportEntryCount;
    
    @JsonProperty("입항일시")
    private String entryDateTime;
    
    @JsonProperty("출항일시")
    private String exitDateTime;
    
    @JsonProperty("상태구분")
    private String status;
    
    @JsonProperty("정보생성일시")
    private String infoCreateDateTime;
}
