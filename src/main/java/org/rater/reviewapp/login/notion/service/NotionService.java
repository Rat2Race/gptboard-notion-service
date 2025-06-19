package org.rater.reviewapp.login.notion.service;

import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.login.config.NotionProperties;
import org.rater.reviewapp.login.notion.dto.NotionRevokeRequest;
import org.rater.reviewapp.login.notion.dto.NotionTokenRequest;
import org.rater.reviewapp.login.notion.dto.NotionTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NotionService {

    private final NotionProperties notion;
    private final RestClient notionClient;

    public NotionTokenResponse generateToken(NotionTokenRequest request) {
        return notionClient.post()
            .uri("/oauth/token")
            .body(request)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
            .header("Notion-Version", notion.getVersion())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(NotionTokenResponse.class);
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
    }

    private String getEncodedCredentials() {
        String credentials = notion.getClientId() + ":" + notion.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
