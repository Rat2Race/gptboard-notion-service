package org.rater.reviewapp.notion.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rater.reviewapp.global.exception.NotionException;
import org.rater.reviewapp.notion.config.NotionProperties;
import org.rater.reviewapp.notion.dto.request.NotionPageRequest;
import org.rater.reviewapp.notion.dto.request.NotionRevokeRequest;
import org.rater.reviewapp.notion.dto.request.NotionSearchRequest;
import org.rater.reviewapp.notion.dto.request.NotionTokenRequest;
import org.rater.reviewapp.notion.dto.response.NotionPageResponse;
import org.rater.reviewapp.notion.dto.response.NotionSearchPageIdResponse;
import org.rater.reviewapp.notion.dto.response.NotionSearchPageIdResponse.PageInfo;
import org.rater.reviewapp.notion.dto.response.NotionSearchResponse;
import org.rater.reviewapp.notion.dto.response.NotionSearchResponse.SearchResult;
import org.rater.reviewapp.notion.dto.response.NotionTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotionApiService {

    private final NotionProperties notion;
    private final RestClient notionClient;

    public NotionTokenResponse fetchToken(NotionTokenRequest request) {
        return handleNotionApiCall(() ->
                notionClient.post()
                    .uri("/oauth/token")
                    .body(request)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncodedCredentials())
                    .header("Notion-Version", notion.getVersion())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(NotionTokenResponse.class),
            "Notion 토큰 발급에 실패했습니다."
        );
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
        return handleNotionApiCall(() -> {
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
            }, "Notion 토큰 삭제 중 오류가 발생했습니다."
        );
    }

    public NotionPageResponse retrievePage(NotionPageRequest request) {
        return handleNotionApiCall(() ->
                notionClient.get()
                    .uri("/pages/{pageId}", request.pageId())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.accessToken())
                    .header("Notion-Version", notion.getVersion())
                    .retrieve()
                    .body(NotionPageResponse.class),
            "Notion 페이지 조회에 실패했습니다."
        );
    }

    public NotionSearchResponse searchPages(NotionSearchRequest request) {
        Map<String, String> body = Map.of("query", request.query());

        return handleNotionApiCall(() ->
                notionClient.post()
                    .uri("/search")
                    .body(body)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + request.accessToken())
                    .header("Notion-Version", notion.getVersion())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(NotionSearchResponse.class),
            "Notion 페이지 검색에 실패했습니다."
        );
    }

//    public NotionSearchPageIdResponse searchPageIds(NotionSearchRequest request) {
//
//        return handleNotionApiCall(() -> {
//                NotionSearchResponse searchResponse = searchPages(request);
//
//                List<PageInfo> pageInfos = searchResponse.results().stream()
//                    .filter(result -> "page".equals(result.object()))
//                    .map(result -> new PageInfo(
//                        result.id(),
//                        result.url(),
//                        extractTitle(result)
//                    ))
//                    .toList();
//                return new NotionSearchPageIdResponse(pageInfos);
//            }, "Notion 페이지 ID 검색에 실패했습니다."
//        );
//    }

    private String getEncodedCredentials() {
        String credentials = notion.getClientId() + ":" + notion.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private <T> T handleNotionApiCall(Supplier<T> action, String errorMsg) {
        try {
            return action.get();
        } catch (Exception e) {
            log.error(errorMsg, e);
            throw new NotionException(errorMsg, e);
        }
    }
}
