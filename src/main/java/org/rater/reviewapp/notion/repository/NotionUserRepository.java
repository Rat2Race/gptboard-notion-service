package org.rater.reviewapp.notion.repository;

import java.util.Optional;
import org.rater.reviewapp.notion.entity.NotionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotionUserRepository extends JpaRepository<NotionUser, Long> {

    Optional<NotionUser> findByUserId(String userId);

    Optional<NotionUser> findByAccessToken(String accessToken);
}
