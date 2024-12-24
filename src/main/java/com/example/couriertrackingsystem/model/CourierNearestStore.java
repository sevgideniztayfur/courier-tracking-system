package com.example.couriertrackingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierNearestStore {

    private String courierId;
    private String storeName;

    public String getUniqueKey() {
        return courierId + storeName;
    }
}
