package com.example.couriertrackingsystem.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierTrackingDto {

    private LocalDateTime time;
    private String courier;
    private Double lat;
    private Double lng;
}
