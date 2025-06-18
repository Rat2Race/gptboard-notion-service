package org.rater.reviewapp.login.notion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotionTokenRequest(
    @JsonProperty("grant_type") String grantType,
    String code,
    @JsonProperty("redirect_uri") String redirectUri
) {

}
