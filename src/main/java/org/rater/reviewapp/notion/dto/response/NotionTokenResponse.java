package org.rater.reviewapp.notion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 액세스 토큰 응답 객체")
public record NotionTokenResponse(
    @Schema(description = "액세스 토큰")
    @JsonProperty("access_token")
    String accessToken,

    @Schema(description = "봇 ID (고유값)")
    @JsonProperty("bot_id")
    String botId,

    @Schema(description = "복제된 템플릿 ID")
    @JsonProperty("duplicated_template_id")
    String duplicatedTemplateId,

    @Schema(description = "워크스페이스 소유자 정보")
    Owner owner,

    @Schema(description = "워크스페이스 아이콘")
    @JsonProperty("workspace_icon")
    String workspaceIcon,

    @Schema(description = "워크스페이스 ID")
    @JsonProperty("workspace_id")
    String workspaceId,

    @Schema(description = "워크스페이스 이름")
    @JsonProperty("workspace_name")
    String workspaceName
) {

    @Schema(description = "워크스페이스 소유자 정보")
    public record Owner(
        @Schema(description = "워크스페이스 존재 여부")
        boolean workspace
    ) {

    }
}
