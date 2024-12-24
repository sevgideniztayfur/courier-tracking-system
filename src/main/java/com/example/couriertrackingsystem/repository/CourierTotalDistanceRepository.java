package com.example.couriertrackingsystem.repository;

import com.example.couriertrackingsystem.model.CourierTotalDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierTotalDistanceRepository extends JpaRepository<CourierTotalDistance, Long> {

    Optional<CourierTotalDistance> findByCourierId(String courierId);
}
