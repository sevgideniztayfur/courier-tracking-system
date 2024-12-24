package com.example.couriertrackingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Courier_Total_Distance")
@NoArgsConstructor
@AllArgsConstructor
public class CourierTotalDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String courierId;
    @NonNull
    private Double distance;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private LocalDateTime updateTime;
}
