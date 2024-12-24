package com.example.couriertrackingsystem.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierTotalDistanceDto {

    private String courierId;
    private Double distance;
    private LocalDateTime startTime;
    private LocalDateTime updateTime;
}
