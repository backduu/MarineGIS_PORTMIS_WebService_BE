package com.bluespace.marine_mis_service.Batch.Writer;

import com.bluespace.marine_mis_service.Repository.VesselControlInfoRepository;
import com.bluespace.marine_mis_service.domain.entity.VesselControlInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 변환된 데이터를 DB에 한꺼번에(Chunk 단위로) 저장하는 클래스입니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VesselControlWriter implements ItemWriter<VesselControlInfo> {

    private final VesselControlInfoRepository repository;

    @Override
    public void write(Chunk<? extends VesselControlInfo> chunk) {
        log.info("DB 저장 시작: {} 건", chunk.getItems().size());
        repository.saveAll(chunk.getItems());
        log.info("DB 저장 완료");
    }
}
