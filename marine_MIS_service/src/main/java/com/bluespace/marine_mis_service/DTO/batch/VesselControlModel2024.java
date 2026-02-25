package com.bluespace.marine_mis_service.DTO.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * uddi:fdcdb0d1-0296-4c3b-8087-8ab4bd4d5123 (20240430) API 모델
 */
@Data
public class VesselControlModel2024 {
    @JsonProperty("항만명")
    private String portName;
    
    @JsonProperty("호출부호")
    private String callSign;
    
    @JsonProperty("년도")
    private Integer year;
    
    @JsonProperty("항차")
    private Integer turn;
    
    @JsonProperty("선박신고항만명")
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
