package org.rater.reviewapp.notion.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.global.exception.NotionException;
import org.rater.reviewapp.notion.dto.request.NotionUserRequest;
import org.rater.reviewapp.notion.dto.response.NotionTokenResponse;
import org.rater.reviewapp.notion.dto.response.NotionUserResponse;
import org.rater.reviewapp.notion.entity.NotionUser;
import org.rater.reviewapp.notion.repository.NotionUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class NotionUserService {

    private final NotionUserRepository notionUserRepository;

    public void saveNotionUser(NotionTokenResponse response) {
        if (response == null || response.botId() == null) {
            throw new NotionException("Notion 응답이 올바르지 않음 (botId null)");
        }
        notionUserRepository.save(NotionUser.of(response.botId(), response.accessToken()));
    }

    public NotionUserResponse getNotionUser(NotionUserRequest request) {
        NotionUser user = notionUserRepository.findByUserId(request.userId())
            .orElseThrow(() -> new NotionException("존재하지 않는 노션 유저입니다: " + request.userId()));

        return NotionUserResponse.from(user);
    }

//    public void deleteNotionUser(NotionUserRequest request) {
//        NotionUser user = notionUserRepository.findByUserId(request.userId())
//            .orElseThrow(() -> new NotionException("존재하지 않는 노션 유저입니다: " + request.userId()));
//
//        if (!user.getAccessToken().equals(request.accessToken())) {
//            throw new NotionException("악의적인 접근입니다.");
//        }
//
//        notionUserRepository.delete(user);
//    }

    public void deleteNotionUser(String accessToken) {
        NotionUser user = notionUserRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new NotionException("존재하지 않는 노션 유저입니다"));

        notionUserRepository.delete(user);
    }
}
