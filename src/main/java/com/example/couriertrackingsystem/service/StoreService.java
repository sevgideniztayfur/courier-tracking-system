package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.mapper.StoreMapper;
import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.repository.StoreRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheManager = "store_cache_manager", cacheNames = "stores")
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    @CachePut(key = "#store.name")
    public void save(Store store) {
        storeRepository.save(store);
    }

    @Cacheable()
    public List<Store> getAll() {
        return storeRepository.findAll();
    }
}
