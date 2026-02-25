package com.bluespace.marine_mis_service.Config;

import com.bluespace.marine_mis_service.Batch.Processor.VesselControl2024Processor;
import com.bluespace.marine_mis_service.Batch.Processor.VesselControl2025Processor;
import com.bluespace.marine_mis_service.Batch.Reader.OdcloudApiItemReader;
import com.bluespace.marine_mis_service.Batch.Writer.VesselControlWriter;
import com.bluespace.marine_mis_service.DTO.batch.OdcloudApiResponse;
import com.bluespace.marine_mis_service.DTO.batch.VesselControlModel2024;
import com.bluespace.marine_mis_service.DTO.batch.VesselControlModel2025;
import com.bluespace.marine_mis_service.domain.entity.VesselControlInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestClient;

/**
 * Spring Batch 전체 설정을 담당하는 클래스
 * Job(전체 작업) -> Step(세부 단계) -> Reader/Processor/Writer 구조로 구성된다.
 */
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final VesselControl2024Processor processor2024;
    private final VesselControl2025Processor processor2025;
    private final VesselControlWriter writer;

    @Value("${odcloud.service-key}")
    private String serviceKey;

    /**
     * 전체 배치 작업(Job) 정의
     */
    @Bean
    public Job vesselControlJob() {
        return new JobBuilder("vesselControlJob", jobRepository)
                .start(vesselControl2024Step()) // 2024년 데이터 처리 단계 시작
                .next(vesselControl2025Step())  // 완료 후 2025년 데이터 처리 단계 진행
                .build();
    }

    /**
     * 2024년 데이터 처리 단계(Step)
     */
    @Bean
    public Step vesselControl2024Step() {
        return new StepBuilder("vesselControl2024Step", jobRepository)
                .<VesselControlModel2024, VesselControlInfo>chunk(100, transactionManager) // 100건씩 묶어서 처리
                .reader(vesselControl2024Reader())
                .processor(processor2024)
                .writer(writer)
                .build();
    }

    /**
     * 2025년 데이터 처리 단계(Step)
     */
    @Bean
    public Step vesselControl2025Step() {
        return new StepBuilder("vesselControl2025Step", jobRepository)
                .<VesselControlModel2025, VesselControlInfo>chunk(100, transactionManager)
                .reader(vesselControl2025Reader())
                .processor(processor2025)
                .writer(writer)
                .build();
    }

    /**
     * API 통신을 위한 RestClient Bean
     */
    @Bean
    public RestClient odcloudRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.odcloud.kr/api")
                .build();
    }

    // 2024년 데이터 Reader
    @Bean
    public OdcloudApiItemReader<VesselControlModel2024> vesselControl2024Reader() {
        return new OdcloudApiItemReader<>(
                odcloudRestClient(),
                "/15128156/v1/uddi:fdcdb0d1-0296-4c3b-8087-8ab4bd4d5123",
                serviceKey,
                new ParameterizedTypeReference<OdcloudApiResponse<VesselControlModel2024>>() {}
        );
    }

    // 2025년 데이터 Reader
    @Bean
    public OdcloudApiItemReader<VesselControlModel2025> vesselControl2025Reader() {
        return new OdcloudApiItemReader<>(
                odcloudRestClient(),
                "/15128156/v1/uddi:184c0fa3-c7a6-453e-9853-c10e2c756309",
                serviceKey,
                new ParameterizedTypeReference<OdcloudApiResponse<VesselControlModel2025>>() {}
        );
    }
}
