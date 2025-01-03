package com.example.couriertrackingsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "Store")
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
}
