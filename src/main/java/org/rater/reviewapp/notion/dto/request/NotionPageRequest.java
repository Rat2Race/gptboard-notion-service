package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 페이지 조회 요청")
public record NotionPageRequest(
    @Schema(description = "Notion API 액세스 토큰")
    @JsonProperty("access_token")
    String accessToken,

    @Schema(description = "조회할 페이지 ID")
    @JsonProperty("page_id")
    String pageId
) {
}
