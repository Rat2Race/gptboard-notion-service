package org.rater.reviewapp.login.notion.service;

import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rater.reviewapp.global.exception.NotionApiException;
import org.rater.reviewapp.login.config.NotionProperties;
import org.rater.reviewapp.login.notion.dto.request.NotionRevokeRequest;
import org.rater.reviewapp.login.notion.dto.request.NotionTokenRequest;
import org.rater.reviewapp.login.notion.dto.response.NotionTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotionService {

    private final NotionProperties notion;
    private final RestClient notionClient;

    public NotionTokenResponse generateToken(NotionTokenRequest request) {
        try {
            return notionClient.post()
                .uri("/oauth/token")
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
                .header("Notion-Version", notion.getVersion())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(NotionTokenResponse.class);
        } catch (Exception e) {
            log.error("Notion 토큰 발급 실패", e);
            throw new NotionApiException("Notion 토큰 발급에 실패했습니다.", e);
        }
    }

    /**
     * 프론트에서 만들면 삭제할 예정
     *
     * @return
     */
    public String getNotionOAuthUrl() {
        return notion.getOauthUrl();
    }

    public boolean revokeToken(NotionRevokeRequest request) {
        try {
            Map<String, String> body = Map.of("token", request.accessToken());
            return notionClient.post()
                .uri("/oauth/revoke")
                .body(body)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .getStatusCode()
                .is2xxSuccessful();
        } catch (Exception e) {
            log.error("Notion 토큰 삭제 실패", e);
            throw new NotionApiException("Notion 토큰 삭제 중 오류가 발생했습니다.", e);
        }
    }

    private String getEncodedCredentials() {
        String credentials = notion.getClientId() + ":" + notion.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
