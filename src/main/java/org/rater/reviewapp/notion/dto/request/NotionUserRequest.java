package org.rater.reviewapp.notion.dto.request;

public record NotionUserRequest(
    String userId,
    String accessToken
) {

}
