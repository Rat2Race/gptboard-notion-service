package org.rater.reviewapp.notion.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 나중에 DTO에 @VALID 적용시킬 예정
 * @param grantType
 * @param code
 * @param redirectUri
 */
@Schema(description = "Notion 액세스 토큰 발급 요청")
public record NotionTokenRequest(
    @Schema(description = "OAuth 인증 방식")
    @JsonProperty("grant_type")
    String grantType,

    @Schema(description = "Notion 인증 페이지에서 받은 코드")
    String code,

    @Schema(description = "OAuth 인증 후 리다이렉트 URI")
    @JsonProperty("redirect_uri")
    String redirectUri
) {

}
