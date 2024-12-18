package com.example.couriertrackingsystem.repository;

import com.example.couriertrackingsystem.model.CourierEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierEventRepository extends JpaRepository<CourierEvent, Long> {
}
