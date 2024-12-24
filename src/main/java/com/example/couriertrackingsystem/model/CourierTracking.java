package com.example.couriertrackingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Courier_Tracking")
@NoArgsConstructor
@AllArgsConstructor
public class CourierTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private LocalDateTime time;
    @NonNull
    private String courierId;
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;

}
