package com.midziklabs.payments.configuration;


import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("token");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(5)));
        return cacheManager;
    }
}
