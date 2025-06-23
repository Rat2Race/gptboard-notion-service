package org.rater.reviewapp.notion.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record NotionSearchPageIdResponse(
    @Schema(description = "페이지 ")
    List<PageInfo> pages
) {

    public record PageInfo(
        @Schema(description = "페이지 ID")
        String id,

        @Schema(description = "페이지 URL")
        String url,

        @Schema(description = "페이지 타이틀")
        String title
    ) {

    }
}