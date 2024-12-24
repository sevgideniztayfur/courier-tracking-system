package com.example.couriertrackingsystem.repository;

import com.example.couriertrackingsystem.model.CourierTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierTrackingRepository extends JpaRepository<CourierTracking, Long> {

    Optional<CourierTracking> findFirstByCourierIdOrderByTimeDesc(String courierId);
}
