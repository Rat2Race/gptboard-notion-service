package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 사용자 요청 객체")
public record NotionUserRequest(
    @Schema(description = "사용자 ID")
    @JsonProperty("user_id")
    String userId,

    @Schema(description = "Notion API 액세스 토큰")
    @JsonProperty("access_token")
    String accessToken
) {

}
