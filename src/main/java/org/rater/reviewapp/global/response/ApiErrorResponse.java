package org.rater.reviewapp.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "API 에러 응답 객체")
public record ApiErrorResponse(
    @Schema(description = "에러 코드", example = "N-502")
    String code,

    @Schema(description = "에러 메시지", example = "노션 연동 중 장애가 발생했습니다.")
    String message,

    @Schema(description = "상세 정보", example = "Failed to connect to Notion API: Connection timeout")
    String detail,

    @Schema(description = "에러 발생 시각", example = "2023-06-17T14:30:15.123")
    LocalDateTime timestamp
) {

    public ApiErrorResponse(String code, String message, String detail) {
        this(code, message, detail, LocalDateTime.now());
    }
}
