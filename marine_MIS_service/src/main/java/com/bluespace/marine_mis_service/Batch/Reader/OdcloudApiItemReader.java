package com.bluespace.marine_mis_service.Batch.Reader;

import com.bluespace.marine_mis_service.DTO.batch.OdcloudApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Iterator;
import java.util.List;

/**
 * 공공데이터 API에서 데이터를 읽어오는 공통 Reader 클래스
 * 페이징 처리를 지원하여 대량의 데이터를 나누어서 읽어온다.
 */
@Slf4j
public class OdcloudApiItemReader<T> implements ItemReader<T> {

    private final RestClient restClient;
    private final String apiUrl;
    private final String serviceKey;
    private final ParameterizedTypeReference<OdcloudApiResponse<T>> responseType;
    
    private int currentPage = 1;
    private final int perPage = 100; // 한 번에 가져올 데이터 양
    private Iterator<T> dataIterator;
    private boolean exhausted = false;

    public OdcloudApiItemReader(RestClient restClient, String apiUrl, String serviceKey, ParameterizedTypeReference<OdcloudApiResponse<T>> responseType) {
        this.restClient = restClient;
        this.apiUrl = apiUrl;
        this.serviceKey = serviceKey;
        this.responseType = responseType;
    }

    /**
     * Spring Batch에서 데이터를 하나씩 읽을 때 호출되는 메서드입니다.
     */
    @Override
    public T read() {
        // 현재 가지고 있는 데이터가 없거나 다 읽었으면 다음 페이지를 가져옵니다.
        if (dataIterator == null || !dataIterator.hasNext()) {
            if (exhausted) {
                return null; // 더 이상 읽을 데이터가 없으면 null 반환 (배치 종료)
            }
            fetchNextPage();
        }

        if (dataIterator != null && dataIterator.hasNext()) {
            return dataIterator.next();
        }

        return null;
    }

    /**
     * API를 호출하여 다음 페이지의 데이터를 가져옵니다.
     */
    private void fetchNextPage() {
        log.info("API 호출 중: {} (Page: {})", apiUrl, currentPage);

        try {
            OdcloudApiResponse<T> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(apiUrl)
                            .queryParam("serviceKey", serviceKey)
                            .queryParam("page", currentPage)
                            .queryParam("perPage", perPage)
                            .build())
                    .retrieve()
                    .body(responseType);

            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                log.info("데이터 가져오기 성공: {} 건", response.getData().size());
                this.dataIterator = response.getData().iterator();
                
                // 가져온 데이터가 perPage보다 적으면 다음 데이터가 없다고 판단합니다.
                if (response.getData().size() < perPage) {
                    exhausted = true;
                }
                currentPage++;
            } else {
                log.info("가져올 데이터가 더 이상 없습니다.");
                exhausted = true;
                this.dataIterator = null;
            }
        } catch (Exception e) {
            log.error("API 호출 중 오류 발생: {}", e.getMessage());
            exhausted = true;
            this.dataIterator = null;
        }
    }
}
