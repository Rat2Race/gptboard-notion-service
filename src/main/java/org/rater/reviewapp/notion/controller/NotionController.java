package org.rater.reviewapp.notion.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.notion.dto.request.IssueNotionTokenRequest;
import org.rater.reviewapp.notion.dto.response.GetNotionContentResponse;
import org.rater.reviewapp.notion.dto.response.GetNotionPageResponse;
import org.rater.reviewapp.notion.dto.response.IssueNotionTokenResponse;
import org.rater.reviewapp.notion.dto.response.SearchNotionResponse;
import org.rater.reviewapp.notion.service.NotionAuthService;
import org.rater.reviewapp.notion.service.NotionPageService;
import org.rater.reviewapp.notion.service.NotionUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name = "Notion API", description = "Notion 서비스 관련 처리 Controller")
public class NotionController {

    private final NotionAuthService notionAuthService;
    private final NotionUserService notionUserService;
    private final NotionPageService notionPageService;

    /**
     * 백엔드 테스트용 / 프론트에서 구현할 예정 프론트에서 인증 링크 제공해서 클라이언트의 코드값을 받아야함
     */
    @GetMapping("/login")
    @Operation(
        summary = "Notion OAuth 로그인 리다이렉트",
        description = "Notion 인증을 위해 외부 인증 페이지로 리다이렉트합니다."
    )
    @ApiResponse(responseCode = "302", description = "Notion 인증 URL로 리다이렉트")
    public void getNotionLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect(notionAuthService.getNotionAuthURL());
    }

    /**
     * 백엔드 테스트용 / 프론트에서 구현할 예정 프론트에서 코드값과 함께 /oauth/token 으로 post request 해줘야함
     */
    @GetMapping("/callback")
    @Hidden
    public String notionCallback(@RequestParam("code") String code) {
        return code;
    }

    @PostMapping("/auth/issue")
    @Operation(
        summary = "Notion 액세스 토큰 발급",
        description = "Notion 인증 코드를 이용해 액세스 토큰을 발급합니다."
    )
    @ApiResponse(responseCode = "200", description = "액세스 토큰 발급 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<IssueNotionTokenResponse> issueNotionToken(
        @RequestBody IssueNotionTokenRequest request) {
        IssueNotionTokenResponse response = notionAuthService.fetchToken(request);
        notionUserService.saveNotionUser(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/auth/revoke")
    @Operation(
        summary = "Notion 액세스 토큰 취소",
        description = "Notion 액세스 토큰을 삭제합니다."
    )
    @ApiResponse(responseCode = "200", description = "액세스 토큰 취소 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<Boolean> revokeNotionToken(
        @Parameter(description = "액세스 토큰 (Bearer ...)", required = true)
        @RequestHeader("Authorization") String accessToken) {
        boolean response = notionAuthService.revokeToken(accessToken);
        notionUserService.deleteNotionUser(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/notion/pages")
    @Operation(
        summary = "Notion 페이지 검색",
        description = "Notion 액세스 토큰과 검색어를 이용해 페이지를 검색합니다. "
            + "전체 페이지 검색 가능 (query에 null값을 넣어서 조회 가능)"
    )
    @ApiResponse(responseCode = "200", description = "페이지 검색 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<SearchNotionResponse> searchNotionPages(
        @RequestParam(required = false, defaultValue = "") String query,
        @Parameter(description = "액세스 토큰 (Bearer ...)", required = true)
        @RequestHeader("Authorization") String accessToken) {
        SearchNotionResponse response = notionPageService.searchPages(accessToken, query);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/notion/page/{pageId}")
    @Operation(
        summary = "Notion 페이지 조회",
        description = "Notion 액세스 토큰과 페이지 ID를 이용해 페이지 정보를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "페이지 조회 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<GetNotionPageResponse> retrieveNotionPage(
        @PathVariable String pageId,
        @Parameter(description = "액세스 토큰 (Bearer ...)", required = true)
        @RequestHeader("Authorization") String accessToken) {
        GetNotionPageResponse response = notionPageService.retrievePage(accessToken, pageId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/notion/blocks/{blockId}/children")
    @Operation(
        summary = "Notion 블록 내용 조회",
        description = "Notion 액세스 토큰과 블록 ID를 이용해 블록 내용을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "블록 내용 조회 성공")
    @ApiResponse(responseCode = "400", description = "입력값 오류")
    @ApiResponse(responseCode = "502", description = "노션 연동 장애")
    public ResponseEntity<GetNotionContentResponse> retrieveNotionContent(
        @PathVariable String blockId,
        @Parameter(description = "액세스 토큰 (Bearer ...)", required = true)
        @RequestHeader("Authorization") String accessToken) {
        GetNotionContentResponse response = notionPageService.retrieveContent(accessToken, blockId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    /**
     * parser 테스트
     * @param blockId
     * @param accessToken
     * @return
     */
    @GetMapping("/notion/blocks/{blockId}/parser")
    public ResponseEntity<List<String>> parserBlock(
        @PathVariable String blockId,
        @Parameter(description = "액세스 토큰 (Bearer ...)", required = true)
        @RequestHeader("Authorization") String accessToken) {
        GetNotionContentResponse rootBlocks = notionPageService.retrieveContent(accessToken, blockId);
        return ResponseEntity.status(HttpStatus.OK).body(notionPageService.extractTexts(accessToken, rootBlocks));
    }
}
