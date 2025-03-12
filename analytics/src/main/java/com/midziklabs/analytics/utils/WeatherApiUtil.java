package com.midziklabs.analytics.utils;

import com.midziklabs.analytics.job.AgeAnalyticsJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WeatherApiUtil {

    private final AgeAnalyticsJob ageAnalyticsJob;

    private WebClient webClient;
    
    @Value("${weather.api.url}")
    private String base_url;
    @Value("${weather.api.key}")
    private String api_key;
    WeatherApiUtil(AgeAnalyticsJob ageAnalyticsJob) {
        this.ageAnalyticsJob = ageAnalyticsJob;
    }

    public WebClient getWebClient(){
        webClient = WebClient.create(base_url);
        return webClient;
    }
    public String getApiKey(){
        return api_key;
    }
    
}
