package org.rater.reviewapp.login.notion.service;

import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.login.notion.dto.NotionTokenRequest;
import org.rater.reviewapp.login.notion.dto.NotionTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NotionService {

    @Value("${NOTION_CLIENT_ID}")
    private String notionClientId;

    @Value("${NOTION_CLIENT_SECRET}")
    private String notionClientSecret;

    @Value("${NOTION_VERSION}")
    private String notionVersion;

    @Value("${NOTION_REDIRECT_URI}")
    private String notionRedirectUrl;

    private final RestClient notionClient;

    public String generateAuthUrl() {
        return "https://api.notion.com/v1/oauth/authorize?"
            + "client_id=" + notionClientId
            + "&response_type=code"
            + "&owner=user"
            + "&redirect_uri=" + notionRedirectUrl;
    }

    public NotionTokenResponse generateToken(NotionTokenRequest request) {
        String credentials = notionClientId + ":" + notionClientSecret;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        return notionClient.post()
            .uri("/oauth/token")
            .body(request)
            .header("Authorization", "Basic " + encoded)
            .header("Notion-Version", notionVersion)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(NotionTokenResponse.class);
    }
}
