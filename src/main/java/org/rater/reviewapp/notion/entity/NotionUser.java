package org.rater.reviewapp.notion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.rater.reviewapp.global.entity.AuditingFields;

@Getter
@Entity
public class NotionUser extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String userId;

    @Column(length = 100)
    private String accessToken;

    protected NotionUser() {}

    private NotionUser(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public static NotionUser of(String userId, String accessToken) {
        return new NotionUser(userId, accessToken);
    }
}
