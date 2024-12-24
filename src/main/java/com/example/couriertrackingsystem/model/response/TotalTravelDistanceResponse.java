package com.example.couriertrackingsystem.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalTravelDistanceResponse {
    private Double totalDistance;
}
