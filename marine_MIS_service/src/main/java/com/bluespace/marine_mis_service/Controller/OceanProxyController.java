package com.bluespace.marine_mis_service.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * <pre>
 * ===========================================================
 * Program Name : OceanProxyController
 * Description  : 국립해양조사원(KHOA) WMS 요청을 대리 수행하는 proxy controller
 * Author       : 백두현
 * Create Date  : 2026-03-09
 * ===========================================================
 * </pre>
 */

@RestController
@RequestMapping("/api/ocean-proxy")
public class OceanProxyController {

    @Value("${KHOA_SERVICE_KEY}")
    private String serviceKey;

    private final String KHOA_WMS_URL = "https://www.khoa.go.kr/oceanmap/otmsWmsApi.do";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/wms")
    public ResponseEntity<byte[]> getOceanWms(
            @RequestParam(value = "layers", required = false) String layers,
            HttpServletRequest request
    ) {
        String bbox = request.getParameter("bbox");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String srs = request.getParameter("srs");

        // [핵심] SSLVectormapApi.do를 호출할 때 파라미터 명을 KHOA 가이드 규격(대문자 등)에 맞춤
        String targetUrl = String.format(
                "https://www.khoa.go.kr/oceanmap/%s/SSLVectormapApi.do?ServiceKey=%s&Layer=%s&BBOX=%s&WIDTH=%s&HEIGHT=%s&SRS=%s&FORMAT=image/png",
                layers, serviceKey, layers, bbox, width, height, srs
        );

        //System.out.println("Final Target URL: " + targetUrl);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));

            // exchange 호출 시 명시적으로 byte[].class 지정
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    byte[].class
            );

            if (response.getBody() == null || response.getBody().length == 0) {
                System.out.println("KHOA 응답 바디가 비어있습니다.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(response.getBody().length)
                    .body(response.getBody());

        } catch (Exception e) {
            e.printStackTrace(); // 에러 스택을 찍어서 어떤 예외인지 정확히 파악
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
