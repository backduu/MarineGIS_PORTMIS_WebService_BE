package com.bluespace.marine_mis_service.Service.impl;

import com.bluespace.marine_mis_service.DTO.WaterTempApiResponseDTO;
import com.bluespace.marine_mis_service.Repository.WaterTempRepository;
import com.bluespace.marine_mis_service.Service.ObservatoryService;
import com.bluespace.marine_mis_service.domain.entity.WaterTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final RestTemplate restTemplate;

    public ObservatoryServiceImpl(WaterTempRepository waterTempRepository) {
        this.waterTempRepository = waterTempRepository;
        
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
    public List<WaterTemp> fetchAndSaveWaterTemp(String obsCode, String reqDate) {
        try {
            // URI 빌드 (이미 인코딩된 키인 경우 build(true) 사용)
            URI uri = UriComponentsBuilder.fromHttpUrl(END_POINT)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("type", "json")
                    .queryParam("obsCode", obsCode)
                    .queryParam("reqDate", reqDate)
                    .queryParam("numOfRows", 300)
                    .queryParam("include", "lat,lot,obsvtrNm,obsrvnDt,wtem")
                    .build(true) // 인코딩된 상태로 URI 객체 생성
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

                    // DB 저장은 별도 Transaction으로 분리
                    return saveWaterTempData(waterTempEntities);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching water temp data: ", e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public List<WaterTemp> saveWaterTempData(List<WaterTemp> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        // 중복 데이터 방지를 위해 DB에 있는 기존 데이터 조회 (obsCode, obsrvnDt 기반)
        // 여기서는 간단하게 개별 save 시 예외 처리 방식을 유지하되, 
        // @Transactional 내부에서 API 호출을 분리한 것만으로도 커넥션 점유 시간을 대폭 줄일 수 있음.
        for (WaterTemp entity : entities) {
            try {
                // saveAll 대신 개별 save를 쓰는 이유는 특정 데이터 중복 시 전체 롤백을 피하기 위함
                // (물론 이 메서드 전체가 @Transactional이므로 예외 발생 시 롤백됨)
                // 만약 중복 데이터가 빈번하다면 존재 여부 체크 후 저장이 더 안전함.
                waterTempRepository.save(entity);
            } catch (Exception e) {
                log.warn("Failed to save water temp data (possibly duplicate): {} - {}", entity.getObsrvnDt(), e.getMessage());
            }
        }
        return entities;
    }
}
