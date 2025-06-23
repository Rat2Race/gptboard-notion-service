package org.rater.reviewapp.notion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

public record NotionPageResponse(
    @Schema(description = "페이지 ID")
    String id,
    
    @Schema(description = "타입")
    String object,
    
    @Schema(description = "페이지 생성일")
    @JsonProperty("created_time")
    String createdTime,
    
    @Schema(description = "페이지 마지막 수정일")
    @JsonProperty("last_edited_time")
    String lastEditedTime,
    
    @Schema(description = "페이지 URL")
    String url,
    
    @Schema(description = "페이지 속성들")
    Map<String, Object> properties,
    
    @Schema(description = "상위 객체")
    Map<String, Object> parent
) {
}