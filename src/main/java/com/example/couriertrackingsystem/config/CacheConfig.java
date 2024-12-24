package com.example.couriertrackingsystem.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(name = "courier_nearest_store_cache_manager")
    @Primary
    public CacheManager courierNearestStoreCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("courier_nearest_store");
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES));
        return caffeineCacheManager;
    }

    @Bean(name = "store_cache_manager")
    public CacheManager storeCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("stores");
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder());
        return caffeineCacheManager;
    }


}
