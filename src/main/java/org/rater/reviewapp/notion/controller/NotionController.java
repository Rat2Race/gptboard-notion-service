package org.rater.reviewapp.notion.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.notion.dto.request.NotionPageRequest;
import org.rater.reviewapp.notion.dto.request.NotionRevokeRequest;
import org.rater.reviewapp.notion.dto.request.NotionSearchRequest;
import org.rater.reviewapp.notion.dto.request.NotionTokenRequest;
import org.rater.reviewapp.notion.dto.response.NotionPageResponse;
import org.rater.reviewapp.notion.dto.response.NotionSearchPageIdResponse;
import org.rater.reviewapp.notion.dto.response.NotionSearchResponse;
import org.rater.reviewapp.notion.dto.response.NotionTokenResponse;
import org.rater.reviewapp.notion.service.NotionApiService;
import org.rater.reviewapp.notion.service.NotionUserService;
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

    private final NotionApiService notionApiService;
    private final NotionUserService notionUserService;

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
        response.sendRedirect(notionApiService.getNotionOAuthUrl());
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
    @ApiResponse(responseCode = "200", description = "액세스 토큰 발급 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<NotionTokenResponse> generateNotionToken(
        @RequestBody NotionTokenRequest request) {
        NotionTokenResponse response = notionApiService.fetchToken(request);
        notionUserService.saveNotionUser(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/oauth/revoke")
    @Operation(
        summary = "Notion 액세스 토큰 취소",
        description = "Notion 액세스 토큰을 삭제합니다."
    )
    @ApiResponse(responseCode = "200", description = "액세스 토큰 취소 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<Boolean> revokeNotionToken(
        @RequestBody NotionRevokeRequest request) {
        boolean response = notionApiService.revokeToken(request);

        notionUserService.deleteNotionUser(request.accessToken());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/notion/page")
    @Operation(
        summary = "Notion 페이지 조회",
        description = "Notion 액세스 토큰과 페이지 ID를 이용해 페이지 정보를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "페이지 조회 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<NotionPageResponse> retrieveNotionPage(
        @RequestBody NotionPageRequest request) {
        NotionPageResponse response = notionApiService.retrievePage(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/notion/search")
    @Operation(
        summary = "Notion 페이지 검색",
        description = "Notion 액세스 토큰과 검색어를 이용해 페이지를 검색합니다."
    )
    @ApiResponse(responseCode = "200", description = "페이지 검색 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<NotionSearchResponse> searchNotionPages(
        @RequestBody NotionSearchRequest request) {
        NotionSearchResponse response = notionApiService.searchPages(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping("/notion/search/page-ids")
//    @Operation(
//        summary = "Notion 페이지 ID 검색",
//        description = "Notion 액세스 토큰과 검색어를 이용해 페이지 ID를 검색합니다."
//    )
//    @ApiResponse(responseCode = "200", description = "페이지 ID 검색 성공")
//    @ApiResponse(responseCode = "400", description = "입력값 오류")
//    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
//    public ResponseEntity<NotionSearchPageIdResponse> searchNotionPageIds(
//        @RequestBody NotionSearchRequest request) {
//        NotionSearchPageIdResponse response = notionApiService.searchPageIds(request);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
