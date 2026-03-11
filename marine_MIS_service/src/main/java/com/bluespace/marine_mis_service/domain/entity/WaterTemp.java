package com.bluespace.marine_mis_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "water_temp", 
       uniqueConstraints = {@UniqueConstraint(columnNames = {"obs_code", "obsrvn_dt"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "obs_code", nullable = false)
    private String obsCode;      // 관측소코드

    @Column(name = "obsvtr_nm")
    private String obsvtrNm;     // 관측소명

    @Column(name = "lat")
    private String lat;          // 위도

    @Column(name = "lon")
    private String lon;          // 경도

    @Column(name = "obsrvn_dt")
    private String obsrvnDt;     // 관측일시 (API에서 제공하는 포맷 그대로 저장하거나 파싱 필요)

    @Column(name = "wtem")
    private String wtem;         // 수온

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
