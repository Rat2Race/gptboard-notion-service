package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 액세스 토큰 취소 요청")
public record NotionRevokeRequest(
    @Schema(description = "취소할 Notion API 액세스 토큰")
    @JsonProperty("access_token")
    String accessToken
) {

}
