package org.rater.reviewapp.login.notion.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.login.notion.dto.NotionTokenRequest;
import org.rater.reviewapp.login.notion.dto.NotionTokenResponse;
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
public class NotionController {

    private final NotionService notionTokenService;

    @GetMapping("/oauth/login")
    public void notionLogin(HttpServletResponse response) throws IOException {
        String authUrl = notionTokenService.generateAuthUrl();
        response.sendRedirect(authUrl);
    }

    @GetMapping("/oauth/callback")
    public String notionCallback(@RequestParam("code") String code) {
        return code;
    }

    @PostMapping("/oauth/token")
    public ResponseEntity<NotionTokenResponse> generateNotionAccessToken(
        @RequestBody NotionTokenRequest notionTokenRequest) {
        NotionTokenResponse response = notionTokenService.generateToken(notionTokenRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
