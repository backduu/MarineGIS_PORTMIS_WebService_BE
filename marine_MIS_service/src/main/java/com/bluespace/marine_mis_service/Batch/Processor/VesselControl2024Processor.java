package com.bluespace.marine_mis_service.Batch.Processor;

import com.bluespace.marine_mis_service.DTO.batch.VesselControlModel2024;
import com.bluespace.marine_mis_service.domain.entity.VesselControlInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 2024년 API 데이터를 DB 엔티티로 변환하는 클래스입니다.
 */
@Component
public class VesselControl2024Processor implements ItemProcessor<VesselControlModel2024, VesselControlInfo> {

    @Override
    public VesselControlInfo process(VesselControlModel2024 item) {
        // API로 받은 데이터를 우리가 DB에 저장할 엔티티 형식으로 옮겨 담습니다.
        return VesselControlInfo.builder()
                .portName(item.getPortName())
                .callSign(item.getCallSign())
                .controlYear(item.getYear())
                .controlCount(item.getTurn())
                .reportPortName(item.getReportPortName())
                .reportYear(item.getReportEntryYear())
                .reportCount(item.getReportEntryCount())
                .entryDateTime(item.getEntryDateTime())
                .exitDateTime(item.getExitDateTime())
                .status(item.getStatus())
                .infoCreateDateTime(item.getInfoCreateDateTime())
                .sourceApi("20240430") // 어떤 API에서 왔는지 구분하기 위함입니다.
                .build();
    }
}
