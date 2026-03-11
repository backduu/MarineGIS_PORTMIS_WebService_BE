package com.bluespace.marine_mis_service.Service;

import com.bluespace.marine_mis_service.domain.entity.ObsLocations;
import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import java.util.List;

public interface ObservatoryService {
    List<WaterTemp> getWaterTempList(String obsCode, String reqDate);
    List<WaterTemp> fetchAndSaveWaterTemp(String obsCode, String reqDate);
    List<ObsLocations> fetchAndSaveObservationLocation(Integer page, Integer size);
}
