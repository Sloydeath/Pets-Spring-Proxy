package com.leverx.proxypets.config;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor.getDestination;
import static com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor.getHttpClient;

@Configuration
public class DestinationConfig {

    @Bean
    public HttpDestination httpDestination() {
        return getDestination("BaseAPI_noAuth").asHttp();
    }

    @Bean
    public HttpClient httpClient() {
        return getHttpClient(httpDestination());
    }
}
