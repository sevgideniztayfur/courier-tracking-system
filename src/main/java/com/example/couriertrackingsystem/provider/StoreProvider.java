package com.example.couriertrackingsystem.provider;

import com.example.couriertrackingsystem.mapper.StoreMapper;
import com.example.couriertrackingsystem.model.dto.StoreDto;
import com.example.couriertrackingsystem.service.StoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class StoreProvider {

    private final StoreService storeService;
    private final StoreMapper storeMapper;
    private final ObjectMapper objectMapper;

    public StoreProvider(StoreService storeService, StoreMapper storeMapper, ObjectMapper objectMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
        this.objectMapper = objectMapper;
    }

    public void saveStores(){

        List<StoreDto> storeDtos = readStoresFromJson();
        for (StoreDto storeDto: storeDtos) {
            storeService.save(storeMapper.mapStoreFrom(storeDto));
        }
    }

    public List<StoreDto> readStoresFromJson() {

        TypeReference<List<StoreDto>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getResourceAsStream("/stores.json");
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error("Could not read stores.json file");
            throw new RuntimeException("Could not read stores.json file", e);
        }
    }
}
