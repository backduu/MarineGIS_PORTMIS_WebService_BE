package com.bluespace.marine_mis_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "obs_locations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"obs_code"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObsLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "obs_code", nullable = false, unique = true)
    private String obsCode;      // 조위관측소 고유번호

    @Column(name = "obs_type")
    private String obsType;      // 관측소 유형 (예: 조위관측소, 조룡관측소 등)

    @Column(name = "obsvtr_nm")
    private String obsvtrNm;     // 조위관측소 명 (한글명)

    @Column(name = "lat")
    private String lat;          // 조위관측소 위도

    @Column(name = "lon")
    private String lon;          // 조위관측소 경도

    @Column(name = "obsvtr_en_nm")
    private String obsvtrEnNm;   // 조위관측소 영문명

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
