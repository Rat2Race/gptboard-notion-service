package org.rater.reviewapp.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient notionClient() {
        return RestClient.builder()
                .baseUrl("https://api.notion.com/v1")
                .build();
    }

}
