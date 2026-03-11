package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.DTO.ObsLocationApiResponseDTO;
import com.bluespace.marine_mis_service.DTO.WaterTempApiResponseDTO;
import com.bluespace.marine_mis_service.Repository.ObsLocationsRepository;
import com.bluespace.marine_mis_service.Repository.WaterTempRepository;
import com.bluespace.marine_mis_service.Service.ObservatoryService;
import com.bluespace.marine_mis_service.domain.entity.ObsLocations;
import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ObservatoryServiceImpl implements ObservatoryService {

    private final WaterTempRepository waterTempRepository;
    private final ObsLocationsRepository obsLocationsRepository;
    private final RestTemplate restTemplate;
    // API호출은 트랜잭션 밖에서 수행하고 DB 저장 로직만 명확하게 트랜잭션으로 감싸는 방식
    // 프록시 문제를 깔끔하게 피할 수 있다.
    private final TransactionTemplate transactionTemplate;

    public ObservatoryServiceImpl(WaterTempRepository waterTempRepository, ObsLocationsRepository obsLocationsRepository, TransactionTemplate transactionTemplate) {
        this.waterTempRepository = waterTempRepository;
        this.obsLocationsRepository = obsLocationsRepository;
        this.transactionTemplate = transactionTemplate;
        
        // Timeout 설정: 네트워크 지연으로 인한 스레드 점유 방지
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5초
        factory.setReadTimeout(5000);    // 5초
        this.restTemplate = new RestTemplate(factory);
    }

    @Value("${api.service-key:2497db635162a74187bbea6bf00c242527842e08d27d97f8ba6639a7e1d00f71}")
    private String serviceKey;

    private final String END_POINT = "https://apis.data.go.kr/1192136/surveyWaterTemp/GetSurveyWaterTempApiService";

    @Override
    @Transactional(readOnly = true)
    public List<WaterTemp> getWaterTempList(String obsCode, String reqDate) {
        // DB에서 먼저 조회 (오늘 데이터가 있는지 등 조건에 따라 로직 분기 가능)
        // 여기서는 간단하게 DB 조회 후 없으면 API 호출하는 방식으로 구현 가능하나, 
        // 실시간성이 중요하므로 API 호출 후 저장하는 fetchAndSave를 주로 사용할 것으로 예상됨.
        return waterTempRepository.findWaterTempByConditions(obsCode, reqDate, reqDate);
    }
    @Override
    public List<ObsLocations> fetchAndSaveObservationLocation(Integer page, Integer size) {
        try {
            // API 요청 URL 설정
            String url = "https://api.odcloud.kr/api/15146602/v1/uddi:81b0665b-4f21-41e8-91f1-d3ecc4a7a3f1";
            
            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("page", page)
                    .queryParam("perPage", size)
                    .queryParam("returnType", "json")
                    .build(true)
                    .toUri();

            log.info("Request ObsLocations URI: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ObsLocationApiResponseDTO> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    ObsLocationApiResponseDTO.class
            );

            ObsLocationApiResponseDTO response = responseEntity.getBody();

            if (response != null && response.getData() != null) {
                List<ObsLocations> locations = response.getData().stream()
                        .map(data -> ObsLocations.builder()
                                .obsCode(data.getObsCode())
                                .obsType(data.getObsType())
                                .obsvtrNm(data.getObsvtrNm())
                                .lat(data.getLat())
                                .lon(data.getLon())
                                .obsvtrEnNm(data.getObsvtrEnNm())
                                .build())
                        .collect(Collectors.toList());

                return transactionTemplate.execute(status -> {
                    return saveObsLocationData(locations);
                });
            }
        } catch (Exception e) {
            log.error("Error fetching observation locations: ", e);
        }
        return Collections.emptyList();
    }

    public List<ObsLocations> saveObsLocationData(List<ObsLocations> entities) {
        // 중복된 관측소 코드가 있을 경우 업데이트 또는 건너뛰기 로직
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        for (ObsLocations entity : entities) {
            try {
                obsLocationsRepository.findByObsCode(entity.getObsCode())
                        .ifPresentOrElse(
                            existing -> {
                                // 기존 데이터가 있으면 정보 업데이트
                                existing.setObsType(entity.getObsType());
                                existing.setObsvtrNm(entity.getObsvtrNm());
                                existing.setLat(entity.getLat());
                                existing.setLon(entity.getLon());
                                existing.setObsvtrEnNm(entity.getObsvtrEnNm());
                                obsLocationsRepository.save(existing);
                            },
                            () -> {
                                // 신규 데이터면 저장
                                obsLocationsRepository.save(entity);
                            }
                        );
            } catch (Exception e) {
                log.warn("Failed to save observation location: {} - {}", entity.getObsCode(), e.getMessage());
            }
        }
        return entities;
    }


    @Override
    public List<WaterTemp> fetchAndSaveWaterTemp(String obsCode, String reqDate) {
        try {
            // URI 빌드 (이미 인코딩된 키인 경우 build(true) 사용)
            URI uri = UriComponentsBuilder.fromUriString(END_POINT)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("type", "json")
                    .queryParam("obsCode", obsCode)
                    .queryParam("reqDate", reqDate)
                    .queryParam("numOfRows", 300)
                    .queryParam("include", "lat,lot,obsvtrNm,obsrvnDt,wtem")
                    .build(true) // 인코딩된 상태로 URI 객체 생성, 이중 인코딩 문제를 피하는데 도움이 된다.
                    .toUri();

            log.info("Request URI: {}", uri);

            // 브라우저인 것처럼 보이도록 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.ALL));
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // exchange를 사용하여 상세 응답 확인
            ResponseEntity<WaterTempApiResponseDTO> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    WaterTempApiResponseDTO.class
            );

            log.info("Response Status: {}", responseEntity.getStatusCode());
            WaterTempApiResponseDTO response = responseEntity.getBody();

            if (response != null && response.getBody() != null && 
                response.getBody().getItems() != null) {
                
                List<WaterTempApiResponseDTO.Item> items = response.getBody().getItems().getItem();
                
                if (items != null) {
                    List<WaterTemp> waterTempEntities = items.stream()
                            .map(item -> WaterTemp.builder()
                                    .obsCode(obsCode)
                                    .obsvtrNm(item.getObsvtrNm())
                                    .lat(item.getLat())
                                    .lon(item.getLon())
                                    .obsrvnDt(item.getObsrvnDt())
                                    .wtem(item.getWtem())
                                    .build())
                            .collect(Collectors.toList());

                    // DB 저장은 트랜잭션 템플릿 안에서 수행하도록 합니다.
                    return transactionTemplate.execute(status -> {
                       return saveWaterTempData(waterTempEntities);
                    });
                }
            }
        } catch (Exception e) {
            log.error("Error fetching water temp data: ", e);
        }
        return Collections.emptyList();
    }

    public List<WaterTemp> saveWaterTempData(List<WaterTemp> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        // 중복 데이터 방지를 위해 DB에 있는 기존 데이터 조회 (obsCode, obsrvnDt 기반)
        for (WaterTemp entity : entities) {
            try {
                // 이미 동일한 관측소 코드와 관측 일시의 데이터가 있는지 확인
                boolean exists = waterTempRepository.existsByObsCodeAndObsrvnDt(entity.getObsCode(), entity.getObsrvnDt());
                
                if (!exists) {
                    waterTempRepository.save(entity);
                } else {
                    log.debug("Water temp data already exists: {} - {}", entity.getObsCode(), entity.getObsrvnDt());
                }
            } catch (Exception e) {
                log.warn("Failed to save water temp data (possibly duplicate): {} - {}", entity.getObsrvnDt(), e.getMessage());
            }
        }
        return entities;
    }
}
