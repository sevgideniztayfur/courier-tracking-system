package com.example.couriertrackingsystem.mapper;

import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.model.dto.StoreDto;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public Store mapStoreFrom(StoreDto storeDto){

        return Store.builder().
                name(storeDto.getName()).
                latitude(storeDto.getLat()).
                longitude(storeDto.getLng()).
                build();

    }
}
