package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 페이지 검색 요청")
public record NotionSearchRequest(
    @Schema(description = "Notion API 액세스 토큰")
    @JsonProperty("access_token")
    String accessToken,

    @Schema(description = "페이지 검색 쿼리")
    String query
) {
}
