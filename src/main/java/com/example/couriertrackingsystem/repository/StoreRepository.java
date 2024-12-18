package com.example.couriertrackingsystem.repository;

import com.example.couriertrackingsystem.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
