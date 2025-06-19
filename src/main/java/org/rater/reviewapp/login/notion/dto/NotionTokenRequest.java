package org.rater.reviewapp.login.notion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record NotionTokenRequest(
    @Schema(description = "OAuth 인증 방식", example = "authorization_code")
    @JsonProperty("grant_type")
    String grantType,

    @Schema(description = "Notion 인증 페이지에서 받은 코드")
    String code,

    @Schema(description = "OAuth 인증 후 리다이렉트 URI")
    @JsonProperty("redirect_uri")
    String redirectUri
) {

}
