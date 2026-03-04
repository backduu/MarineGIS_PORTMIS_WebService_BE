package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.Coastline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoastlineRepository extends JpaRepository<Coastline, Long>, CoastlineRepositoryCustom {
}
