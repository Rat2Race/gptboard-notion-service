package org.rater.reviewapp.login.notion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotionTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("bot_id") String botId,
    @JsonProperty("duplicated_template_id") String duplicatedTemplateId,
    Owner owner,
    @JsonProperty("workspace_icon") String workspaceIcon,
    @JsonProperty("workspace_id") String workspaceId,
    @JsonProperty("workspace_name") String workspaceName
) {

    public static record Owner(
        boolean workspace
    ) {

    }
}
