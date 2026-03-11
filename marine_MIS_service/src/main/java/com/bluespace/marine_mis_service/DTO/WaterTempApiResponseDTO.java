package com.bluespace.marine_mis_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterTempApiResponseDTO {

    private Header header;
    private Body body;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private String type;

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String lat;         // 위도
        private String lon;         // 경도
        private String obsvtrNm;    // 관측소명
        private String obsrvnDt;    // 관측일시
        private String wtem;        // 수온
        
        @JsonProperty("lot")
        public void setLot(String lot) {
            this.lon = lot;
        }
    }
}
