package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.model.CourierNearestStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "courier_nearest_store_cache_manager", cacheNames = "courier_nearest_store")
public class CourierNearestStoreCacheService {

    @CachePut( key = "#courierNearestStore.getUniqueKey()")
    public CourierNearestStore save(CourierNearestStore courierNearestStore) {
        return courierNearestStore;
    }

    @Cacheable(key = "#courierId + #storeName")
    public Optional<CourierNearestStore> findBy(String courierId, String storeName) {
        return Optional.empty();
    }

    @CacheEvict()
    public void evictCache(){
        log.info("Courier Nearest Store cache evicted.");
    }
}
