package com.bluespace.marine_mis_service.Batch.Job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 정해진 시간에 배치를 실행시키는 스케줄러 클래스입니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VesselControlScheduler {

    private final JobLauncher jobLauncher;
    private final Job vesselControlJob;

    /**
     * 매년 9월 1일에 실행되도록 설정 (Cron 표현식: 초 분 시 일 월 요일)
     */
    @Scheduled(cron = "0 0 0 1 9 ?")
    public void runVesselControlJob() {
        log.info("배치 작업 시작 예약: {}", LocalDateTime.now());

        try {
            // 배치 실행 시 매번 새로운 파라미터를 주어 중복 실행 방지 및 이력 관리를 합니다.
            JobParameters params = new JobParametersBuilder()
                    .addLocalDateTime("executeDate", LocalDateTime.now())
                    .toJobParameters();

            jobLauncher.run(vesselControlJob, params);
            log.info("배치 작업이 성공적으로 실행되었습니다.");
        } catch (Exception e) {
            log.error("배치 작업 실행 중 오류 발생: {}", e.getMessage());
        }
    }
}
