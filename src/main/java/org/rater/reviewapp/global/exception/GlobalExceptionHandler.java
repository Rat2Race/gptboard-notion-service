package org.rater.reviewapp.global.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.naming.AuthenticationException;
import org.rater.reviewapp.global.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotionApiException.class)
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<ApiErrorResponse> handleNotionApiException(NotionApiException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(new ApiErrorResponse(
                "N-502",
                "노션 연동 중 장애가 발생했습니다.",
                e.getMessage()
            ));
    }

    // 입력값 오류 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiErrorResponse(
                "C-400",
                "입력값이 올바르지 않습니다.",
                e.getMessage()
            ));
    }

    // 인증 오류 (401)
    @ExceptionHandler(AuthenticationException.class)
    @ApiResponse(responseCode = "401", description = "인증 오류")
    public ResponseEntity<ApiErrorResponse> handleAuth(AuthenticationException e) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ApiErrorResponse(
                "A-401",
                "인증이 필요합니다.",
                e.getMessage()
            ));
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "서버 내부 에러")
    public ResponseEntity<ApiErrorResponse> handleAll(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiErrorResponse(
                "G-500",
                "서버 내부 에러가 발생했습니다.",
                e.getMessage()
            ));
    }

}
