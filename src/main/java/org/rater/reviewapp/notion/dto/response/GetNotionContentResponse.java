package org.rater.reviewapp.notion.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.rater.reviewapp.notion.dto.NotionTextBlockDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetNotionContentResponse(
    String object,

    List<NotionTextBlockDto> results,

    @JsonProperty("next_cursor")
    String nextCursor,

    @JsonProperty("has_more")
    boolean hasMore
) {

}
