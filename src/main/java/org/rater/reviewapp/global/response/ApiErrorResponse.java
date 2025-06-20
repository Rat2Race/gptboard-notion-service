package org.rater.reviewapp.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "API 에러 응답 객체")
public record ApiErrorResponse(
    @Schema(description = "에러 코드")
    String code,

    @Schema(description = "에러 메시지")
    String message,

    @Schema(description = "상세 정보")
    String detail,

    @Schema(description = "에러 발생 시각")
    LocalDateTime timestamp
) {

    public ApiErrorResponse(String code, String message, String detail) {
        this(code, message, detail, LocalDateTime.now());
    }
}
