package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.ObsLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObsLocationsRepository extends JpaRepository<ObsLocations, Long> {
    Optional<ObsLocations> findByObsCode(String obsCode);
    boolean existsByObsCode(String obsCode);
}
