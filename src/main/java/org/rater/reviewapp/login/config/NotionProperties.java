package org.rater.reviewapp.login.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "notion")
public class NotionProperties {

    private final String clientId;
    private final String clientSecret;
    private final String version;
    private final String redirectUri;
    private final String oauthUrl;

}
