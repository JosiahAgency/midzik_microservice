package com.midziklabs.analytics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.midziklabs.analytics.AnalyticsApplication;
import com.midziklabs.analytics.job.AgeAnalyticsJob;
import com.midziklabs.analytics.model.AgeAnalyticsModel;
import com.midziklabs.analytics.model.GenderAnalyticsModel;
import com.midziklabs.analytics.model.ViewCountAnalyticsModel;
import com.midziklabs.analytics.model.WeatherApiModel;
import com.midziklabs.analytics.service.AnalyticsService;
import com.midziklabs.analytics.service.WeatherApiService;
import com.midziklabs.analytics.utils.WeatherApiUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsApplication analyticsApplication;

    private final WeatherApiUtil weatherApiUtil;

    private final AgeAnalyticsJob ageAnalyticsJob;

    private final AnalyticsService firestoreService;
    private final WeatherApiService weatherApiService;

    @GetMapping("/gender/{id}")
    public ResponseEntity<?> getGenderAnalytics(@PathVariable("id") String ad_id) {
        GenderAnalyticsModel model = firestoreService.getGenderAnalytics(ad_id);
        return ResponseEntity.ok().body(model);
    }
    @GetMapping("/age/{id}")
    public ResponseEntity<?> getAgeAnalytics(@PathVariable("id") String ad_id) {
        AgeAnalyticsModel model = firestoreService.getAgeAnalytics(ad_id);
        return ResponseEntity.ok().body(model);
    }
    @GetMapping("/view_count/{id}")
    public ResponseEntity<?> getViewCOuntAnalytics(@PathVariable("id") String ad_id) {
        ViewCountAnalyticsModel model = firestoreService.getViewCountAnalytics(ad_id);
        return ResponseEntity.ok().body(model);
    }
    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam("lon") String longitude, @RequestParam("lat") String latitude) {
        Mono<WeatherApiModel> response = weatherApiService.getCurrentWeather(Double.valueOf(longitude), Double.valueOf(latitude));
        Optional<WeatherApiModel> weather = response.blockOptional();
        if(weather.isPresent()){
            return ResponseEntity.ok().body(weather.get());
        } else {
            return ResponseEntity.internalServerError().body("An error occurred while trying to fetch weather data from API");
        }
    }
    
    
    
    


}
