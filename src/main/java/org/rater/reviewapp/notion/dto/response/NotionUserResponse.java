package org.rater.reviewapp.notion.dto.response;

import lombok.Builder;
import org.rater.reviewapp.notion.entity.NotionUser;

@Builder
public record NotionUserResponse(
    String userId,
    String accessToken
) {
    public static NotionUserResponse from(NotionUser user) {
        return NotionUserResponse.builder()
            .userId(user.getUserId())
            .accessToken(user.getAccessToken())
            .build();
    }
}
