package org.rater.reviewapp.notion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notion 텍스트 블록 DTO (텍스트 추출 전용)")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = NotionTextBlockDtoDeserializer.class)
public record NotionTextBlockDto(
    @Schema(description = "블록 ID")
    String id,

    @Schema(description = "자식 블록 여부")
    @JsonProperty("has_children")
    Boolean hasChildren,

    @Schema(description = "블록 타입")
    String type,

    @Schema(description = "데이터 타입")
    JsonNode data
) {
}
