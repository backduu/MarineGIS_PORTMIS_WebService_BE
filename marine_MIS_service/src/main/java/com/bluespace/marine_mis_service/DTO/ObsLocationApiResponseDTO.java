package com.bluespace.marine_mis_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ObsLocationApiResponseDTO {
    private int page;
    private int perPage;
    private int totalCount;
    private int currentCount;
    private int matchCount;
    private List<Data> data;

    @Getter
    @Setter
    public static class Data {
        @JsonProperty("조위관측소 고유번호")
        private String obsCode;
        
        @JsonProperty("관측소 유형")
        private String obsType;
        
        @JsonProperty("조위관측소 명")
        private String obsvtrNm;
        
        @JsonProperty("조위관측소 위도")
        private String lat;
        
        @JsonProperty("조위관측소 경도")
        private String lon;
        
        @JsonProperty("조위관측소 영문명")
        private String obsvtrEnNm;
    }
}
