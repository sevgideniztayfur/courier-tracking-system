package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DistanceCalculatorServiceTest {

    @InjectMocks
    private DistanceCalculatorService distanceCalculatorService;
    @Mock
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindNearStoresWithinRadius() {
        //given
        Store store1 = new Store();
        store1.setLatitude(40.0);
        store1.setLongitude(29.0);

        Store store2 = new Store();
        store2.setLatitude(41.0);
        store2.setLongitude(30.0);

        List<Store> allStores = Arrays.asList(store1, store2);
        when(storeService.getAll()).thenReturn(allStores);

        double currentLat = 40.0;
        double currentLng = 29.0;
        int radius = 200000; // 200 km

        //when
        List<Store> nearStores = distanceCalculatorService.findNearStoresWithinRadius(radius, currentLat, currentLng);

        //then
        assertEquals(2, nearStores.size());
        verify(storeService, times(1)).getAll();
    }

    @Test
    void testCalculateDistance() {
        //given
        double fromLat = 40.0;
        double fromLng = 29.0;
        double toLat = 41.0;
        double toLng = 30.0;

        //when
        double distance = distanceCalculatorService.calculateDistance(fromLat, fromLng, toLat, toLng);

        //then
        double expectedDistance = 139528.0;
        assertEquals(expectedDistance, distance, 1000.0);
    }
}
