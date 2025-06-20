package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record NotionRevokeRequest(
    @Schema(description = "액세스 토큰")
    @JsonProperty("access_token")
    String accessToken
) {

}

