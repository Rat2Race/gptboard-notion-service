package org.rater.reviewapp.notion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.rater.reviewapp.notion.entity.NotionUser;

@Schema(description = "Notion 사용자 응답 객체")
@Builder
public record NotionUserResponse(
    @Schema(description = "사용자 ID")
    @JsonProperty("user_id")
    String userId,

    @Schema(description = "Notion API 액세스 토큰")
    @JsonProperty("access_token")
    String accessToken
) {
    public static NotionUserResponse from(NotionUser user) {
        return NotionUserResponse.builder()
            .userId(user.getUserId())
            .accessToken(user.getAccessToken())
            .build();
    }
}
