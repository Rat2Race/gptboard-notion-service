package org.rater.reviewapp.notion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record NotionSearchResponse(
    @Schema(description = "객체 타입")
    String object,
    
    @Schema(description = "서치된 결과물 리스트")
    List<SearchResult> results,
    
    @Schema(description = "다음 페이지 정보")
    @JsonProperty("next_cursor")
    String nextCursor,
    
    @Schema(description = "결과값 더 있는지")
    @JsonProperty("has_more")
    boolean hasMore
) {
    public record SearchResult(
        @Schema(description = "객체 ID")
        String id,
        
        @Schema(description = "객체 타입 (page, database, etc)")
        String object,
        
        @Schema(description = "객체 URL")
        String url,
        
        @Schema(description = "생성일")
        @JsonProperty("created_time")
        String createdTime,
        
        @Schema(description = "마지막 수정일")
        @JsonProperty("last_edited_time")
        String lastEditedTime,
        
        @Schema(description = "상위 객체 정보")
        Object parent,
        
        @Schema(description = "객체 속성")
        Object properties
    ) {}
}