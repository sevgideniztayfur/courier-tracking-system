package com.example.couriertrackingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "Courier_Event")
@NoArgsConstructor
@AllArgsConstructor
public class CourierEvent {

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
