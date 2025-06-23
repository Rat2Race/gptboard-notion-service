package org.rater.reviewapp.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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

//    //적용안됨;;
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
//        return objectMapper;
//    }
}
