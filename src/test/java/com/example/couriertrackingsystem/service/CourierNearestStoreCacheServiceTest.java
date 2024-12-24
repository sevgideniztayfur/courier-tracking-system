package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.model.CourierNearestStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CourierNearestStoreCacheServiceTest {

    @InjectMocks
    private CourierNearestStoreCacheService courierNearestStoreCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        //given
        CourierNearestStore store = new CourierNearestStore();
        store.setCourierId("courier1");
        store.setStoreName("store1");

        //when
        CourierNearestStore result = courierNearestStoreCacheService.save(store);

        //then
        assertEquals(store, result);
    }

    @Test
    void testFindBy() {
        //given
        String courierId = "courier1";
        String storeName = "store1";

        //when
        Optional<CourierNearestStore> result = courierNearestStoreCacheService.findBy(courierId, storeName);

        //then
        assertTrue(result.isEmpty());
    }
}
