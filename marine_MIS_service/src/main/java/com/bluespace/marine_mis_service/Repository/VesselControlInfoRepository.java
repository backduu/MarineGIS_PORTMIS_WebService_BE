package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.VesselControlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 선박관제정보 DB 접근을 위한 레포지토리입니다.
 */
@Repository
public interface VesselControlInfoRepository extends JpaRepository<VesselControlInfo, Long> {
}
