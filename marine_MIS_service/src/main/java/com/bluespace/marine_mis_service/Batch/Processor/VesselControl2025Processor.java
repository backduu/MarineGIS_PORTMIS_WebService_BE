package com.bluespace.marine_mis_service.Batch.Processor;

import com.bluespace.marine_mis_service.DTO.batch.VesselControlModel2025;
import com.bluespace.marine_mis_service.domain.entity.VesselControlInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 2025년 API 데이터를 DB 엔티티로 변환하는 클래스입니다.
 */
@Component
public class VesselControl2025Processor implements ItemProcessor<VesselControlModel2025, VesselControlInfo> {

    @Override
    public VesselControlInfo process(VesselControlModel2025 item) {
        // 2025년 데이터 모델에 맞춰서 엔티티에 값을 채워줍니다.
        return VesselControlInfo.builder()
                .portName(item.getPortName())
                .callSign(item.getCallSign())
                .controlYear(item.getControlEntryYear())
                .controlCount(item.getControlEntryCount())
                .reportPortName(item.getReportPortName())
                .reportYear(item.getReportEntryYear())
                .reportCount(item.getReportEntryCount())
                .entryDateTime(item.getEntryDateTime())
                .exitDateTime(item.getExitDateTime())
                .status(item.getStatus())
                .infoCreateDateTime(item.getInfoCreateDateTime())
                .sourceApi("20250731")
                .build();
    }
}
