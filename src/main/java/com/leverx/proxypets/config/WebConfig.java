package com.leverx.proxypets.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.leverx.proxypets.util.PetsApiConstraintUtil.PETS_API_BASE_URL;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Configuration
public class WebConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(PETS_API_BASE_URL)
                .defaultHeader(CONTENT_TYPE)
                .defaultHeader(USER_AGENT)
                .build();
    }
}
