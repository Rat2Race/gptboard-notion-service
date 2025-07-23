package org.rater.reviewapp.notion.service;

import static org.rater.reviewapp.global.util.ApiCallUtil.handleApiCall;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rater.reviewapp.global.exception.NotionException;
import org.rater.reviewapp.notion.config.NotionProperties;
import org.rater.reviewapp.notion.dto.NotionTextBlockDto;
import org.rater.reviewapp.notion.dto.response.GetNotionContentResponse;
import org.rater.reviewapp.notion.dto.response.GetNotionPageResponse;
import org.rater.reviewapp.notion.dto.response.SearchNotionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotionPageService {

    private final NotionProperties notion;
    private final RestClient notionClient;

    public SearchNotionResponse searchPages(String accessToken, String query) {
        Map<String, String> body = Map.of("query", query);
        log.info("query = {}", query);

        return handleApiCall(() ->
                notionClient.post()
                    .uri("/search")
                    .body(body)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .header("Notion-Version", notion.getVersion())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(SearchNotionResponse.class),
            "Notion 페이지 검색에 실패했습니다.",
            NotionException::new
        );
    }

    public GetNotionPageResponse retrievePage(String accessToken, String pageId) {
        System.out.println("[DEBUG] 요청 pageId: " + pageId);
        System.out.println("[DEBUG] Authorization: " + accessToken);
        System.out.println("[DEBUG] 요청 URI: /pages/" + pageId);

        return handleApiCall(() ->
                notionClient.get()
                    .uri("/pages/{pageId}", pageId)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .header("Notion-Version", notion.getVersion())
                    .retrieve()
                    .body(GetNotionPageResponse.class),
            "Notion 페이지 조회에 실패했습니다.",
            NotionException::new
        );
    }

    /**
     * accessToken, blockId를 가지고 retrieve한다.
     *
     * @param accessToken
     * @param blockId
     * @return
     */
    public GetNotionContentResponse retrieveContent(String accessToken, String blockId) {
        return handleApiCall(() ->
                notionClient.get()
                    .uri("/blocks/{blockId}/children", blockId)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .header("Notion-Version", notion.getVersion())
                    .retrieve()
                    .body(GetNotionContentResponse.class),
            "Notion 블록 상세조회에 실패했습니다.",
            NotionException::new
        );
    }

    public List<String> extractTexts(String accessToken, GetNotionContentResponse response) {
        List<NotionTextBlockDto> results = response.results();
        List<String> texts = new ArrayList<>();

        log.info("[NotionPageService] results = {}", results);

        for (NotionTextBlockDto result : results) {
            texts.addAll(extractAllTextsRecursion(result,
                id -> retrieveContent(accessToken, id).results()));
        }

        return texts;
    }

    /**
     * 재귀 호출하면서 모든 depth 블록들을 호출해서 파싱함
     *
     * @param notionBlock       ( extractPlainTextsFromNotionBlock(notionBlock) )
     * @param fetchChildrenFunc ( notionBlock.id -> List<NotionTextBlockDto> )
     * @return
     */
    private List<String> extractAllTextsRecursion(NotionTextBlockDto notionBlock,
        Function<String, List<NotionTextBlockDto>> fetchChildrenFunc) {
        List<String> plainTextList = extractTextsFromRichText(notionBlock.data().get("rich_text"));

        if (notionBlock.hasChildren()) {
            List<NotionTextBlockDto> results = fetchChildrenFunc.apply(notionBlock.id());

            for (NotionTextBlockDto result : results) {
                plainTextList.addAll(extractAllTextsRecursion(result, fetchChildrenFunc));
            }
        }

        return plainTextList;
    }

    private List<String> extractTextsFromRichText(JsonNode richTextList) {
        List<String> plainTextList = new ArrayList<>();

        if (richTextList != null && richTextList.isArray()) {
            for (JsonNode richText : richTextList) {
                plainTextList.add(richText.get("plain_text").asText());
            }
        }

        return plainTextList;
    }
}
