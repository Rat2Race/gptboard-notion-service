package org.rater.reviewapp.notion.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rater.reviewapp.global.exception.NotionException;
import org.rater.reviewapp.notion.dto.response.IssueNotionTokenResponse;
import org.rater.reviewapp.notion.entity.NotionUser;
import org.rater.reviewapp.notion.repository.NotionUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class NotionUserService {

    private final NotionUserRepository notionUserRepository;

    public void saveNotionUser(IssueNotionTokenResponse response) {
        if (response == null || response.botId() == null) {
            throw new NotionException("Notion 응답이 올바르지 않음 (botId null)");
        }
        notionUserRepository.save(NotionUser.of(response.botId(), response.accessToken()));
    }

    public void deleteNotionUser(String accessToken) {
        NotionUser user = notionUserRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new NotionException("존재하지 않는 노션 유저입니다"));
        notionUserRepository.delete(user);
    }
}
