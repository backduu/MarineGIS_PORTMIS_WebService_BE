package com.bluespace.marine_mis_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 선박관제정보 엔티티 (2024년 및 2025년 통합 모델)
 * 공공데이터 API에서 가져온 데이터를 DB에 저장하기 위한 클래스
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vessel_control_info")
public class VesselControlInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 항만명 또는 관제항구
    @Column(name = "port_name")
    private String portName;

    // 호출부호
    @Column(name = "call_sign")
    private String callSign;

    // 년도 또는 관제입항년도
    @Column(name = "control_year")
    private Integer controlYear;

    // 항차 또는 관제입항횟수
    @Column(name = "control_count")
    private Integer controlCount;

    // 선박신고항만명 또는 선박신고항구
    @Column(name = "report_port_name")
    private String reportPortName;

    // 신고입항년도
    @Column(name = "report_year")
    private Integer reportYear;

    // 신고입항횟수
    @Column(name = "report_count")
    private Integer reportCount;

    // 입항일시
    @Column(name = "entry_date_time")
    private String entryDateTime;

    // 출항일시
    @Column(name = "exit_date_time")
    private String exitDateTime;

    // 상태구분
    @Column(name = "status")
    private String status;

    // 정보생성일시
    @Column(name = "info_create_date_time")
    private String infoCreateDateTime;

    // 데이터 소스 구분 (20240430 또는 20250731)
    @Column(name = "source_api")
    private String sourceApi;

    // 로컬 저장 일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
