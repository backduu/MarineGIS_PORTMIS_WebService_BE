package com.bluespace.marine_mis_service.Repository;

import com.bluespace.marine_mis_service.domain.entity.Coastline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface CoastlineRepository extends JpaRepository<Coastline, Long>, CoastlineRepositoryCustom {

    @Query(value = "SELECT ST_AsGeoJSON(ST_Centroid(ST_Union(geom))) as \"centerJson\", " +
                   "CAST(ST_Extent(geom) AS text) as \"bbox\" " +
                   "FROM all_countries_coastline_2025 " +
                   "WHERE sgg_nam = :sggNam", nativeQuery = true)
    Optional<Map<String, Object>> findLocationBySggNam(@Param("sggNam") String sggNam);
}
