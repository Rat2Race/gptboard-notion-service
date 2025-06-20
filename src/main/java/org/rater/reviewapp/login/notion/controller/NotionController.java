package org.rater.reviewapp.login.notion.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.login.notion.dto.request.NotionRevokeRequest;
import org.rater.reviewapp.login.notion.dto.request.NotionTokenRequest;
import org.rater.reviewapp.login.notion.dto.response.NotionTokenResponse;
import org.rater.reviewapp.login.notion.service.NotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name = "Notion API", description = "Notion 서비스 관련 처리 Controller")
public class NotionController {

    private final NotionService notionService;

    /**
     * 백엔드 테스트용 / 프론트에서 구현할 예정 프론트에서 인증 링크 제공해서 클라이언트의 코드값을 받아야함
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/oauth/login")
    @Operation(
        summary = "Notion OAuth 로그인 리다이렉트",
        description = "Notion 인증을 위해 외부 인증 페이지로 리다이렉트합니다."
    )
    @ApiResponse(responseCode = "302", description = "Notion 인증 URL로 리다이렉트")
    public void notionLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(notionService.getNotionOAuthUrl());
    }

    /**
     * 백엔드 테스트용 / 프론트에서 구현할 예정 프론트에서 코드값과 함께 /oauth/token 으로 post request 해줘야함
     *
     * @param code
     * @return
     */
    @GetMapping("/oauth/callback")
    @Hidden
    public String notionCallback(@RequestParam("code") String code) {
        return code;
    }

    @PostMapping("/oauth/token")
    @Operation(
        summary = "Notion 액세스 토큰 발급",
        description = "Notion 인증 코드를 이용해 액세스 토큰을 발급합니다."
    )
    public ResponseEntity<NotionTokenResponse> generateNotionToken(
        @RequestBody NotionTokenRequest request) {
        NotionTokenResponse response = notionService.generateToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/oauth/revoke")
    @Operation(
        summary = "Notion 액세스 토큰 취소",
        description = "Notion 액세스 토큰을 삭제합니다."
    )
    public ResponseEntity<Boolean> revokeNotionToken(
        @RequestBody NotionRevokeRequest request) {
        boolean response = notionService.revokeToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
