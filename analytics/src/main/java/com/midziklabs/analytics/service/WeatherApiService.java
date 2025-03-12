package com.midziklabs.analytics.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.midziklabs.analytics.job.AgeAnalyticsJob;
import com.midziklabs.analytics.model.WeatherApiModel;
import com.midziklabs.analytics.utils.WeatherApiUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

    private final AgeAnalyticsJob ageAnalyticsJob;

    private final WeatherApiUtil weatherApiUtil;

    public Mono<WeatherApiModel> getCurrentWeather(Double longitude, Double latitude){
        WebClient webClient = weatherApiUtil.getWebClient();
        String apiKey = weatherApiUtil.getApiKey();
        Mono<WeatherApiModel> response = webClient.get()
            // .uri("lat={latitude}&lon={longitude}&appid={api_key}&units=metric", latitude, longitude, apiKey)
            .uri(uriBuilder -> uriBuilder
                .path("/weather")
                .queryParam("appid", apiKey)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("units", "metric")
                .build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WeatherApiModel.class);
        return response;
    }


}
