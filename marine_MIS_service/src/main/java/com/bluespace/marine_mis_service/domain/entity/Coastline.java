package com.bluespace.marine_mis_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 전구국가해안선_2025 엔티티
 * all_countries_coastline_2025 테이블과 매핑
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "all_countries_coastline_2025")
public class Coastline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시군구명
    @Column(name = "sgg_nam")
    private String sggNam;

    // TODO: 필요한 다른 컬럼이 있다면 추가 가능. 
    // 현재는 sgg_nam 중복 제거 조회만 필요하므로 최소한으로 구성.
}
