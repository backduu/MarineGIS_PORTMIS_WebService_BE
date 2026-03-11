package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterTempRepository extends JpaRepository<WaterTemp, Long>, WaterTempRepositoryCustom {
}
