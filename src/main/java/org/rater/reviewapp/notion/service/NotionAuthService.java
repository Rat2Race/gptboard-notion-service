package org.rater.reviewapp.notion.service;

import static org.rater.reviewapp.global.util.ApiCallUtil.handleApiCall;

import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rater.reviewapp.global.exception.NotionException;
import org.rater.reviewapp.notion.config.NotionProperties;
import org.rater.reviewapp.notion.dto.request.IssueNotionTokenRequest;
import org.rater.reviewapp.notion.dto.response.IssueNotionTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotionAuthService {

    private final NotionProperties notion;
    private final RestClient notionClient;

    public String getNotionAuthURL() {
        return notion.getOauthUrl();
    }

    public IssueNotionTokenResponse fetchToken(IssueNotionTokenRequest request) {
        return handleApiCall(() ->
                notionClient.post()
                    .uri("/oauth/token")
                    .body(request)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
                    .header("Notion-Version", notion.getVersion())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(IssueNotionTokenResponse.class),
            "Notion 토큰 발급에 실패했습니다.",
            NotionException::new
        );
    }

    public boolean revokeToken(String accessToken) {
        return handleApiCall(() -> {
                Map<String, String> body = Map.of("token", extractBearerToken(accessToken));

                return notionClient.post()
                    .uri("/oauth/revoke")
                    .body(body)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity()
                    .getStatusCode()
                    .is2xxSuccessful();
            },
            "Notion 토큰 삭제 중 오류가 발생했습니다.",
            NotionException::new
        );
    }

    private String getEncodedCredentials() {
        String credentials = notion.getClientId() + ":" + notion.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        if (authorizationHeader.toLowerCase().startsWith("bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        return null;
    }
}
