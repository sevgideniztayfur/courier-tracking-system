package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.mapper.CourierTrackingMapper;
import com.example.couriertrackingsystem.model.CourierNearestStore;
import com.example.couriertrackingsystem.model.CourierTracking;
import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.repository.CourierTrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CourierTrackingServiceTest {

    @InjectMocks
    private CourierTrackingService courierTrackingService;
    @Mock
    private CourierTrackingRepository courierTrackingRepository;
    @Mock
    private CourierTrackingMapper courierTrackingMapper;
    @Mock
    private CourierTotalDistanceService courierTotalDistanceService;
    @Mock
    private DistanceCalculatorService distanceCalculatorService;
    @Mock
    private CourierNearestStoreCacheService courierNearestStoreCacheService;
    @Mock
    private StoreService storeService;
    @Mock
    private Logger log;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewCourierTracking() {
        //given
        CourierTrackingDto courierTrackingDto = new CourierTrackingDto();
        courierTrackingDto.setCourier("courier1");
        courierTrackingDto.setLat(10.0);
        courierTrackingDto.setLng(20.0);

        CourierTracking courierTracking = new CourierTracking();
        when(courierTrackingMapper.mapCourierTrackingFrom(courierTrackingDto)).thenReturn(courierTracking);
        when(distanceCalculatorService.findNearStoresWithinRadius(anyInt(), anyDouble(), anyDouble())).thenReturn(List.of());

        //when
        courierTrackingService.save(courierTrackingDto);

        //then
        verify(courierTrackingRepository, times(1)).save(courierTracking);
        verify(courierTotalDistanceService, times(1)).saveInitialTotalDistance(courierTrackingDto);
        verify(distanceCalculatorService, times(1)).findNearStoresWithinRadius(100, 10.0, 20.0);
    }

    @Test
    void testSave_ExistingCourierTracking() {
        //given
        CourierTrackingDto courierTrackingDto = new CourierTrackingDto();
        courierTrackingDto.setCourier("courier1");
        courierTrackingDto.setLat(10.0);
        courierTrackingDto.setLng(20.0);

        CourierTracking lastTracking = new CourierTracking();
        when(courierTrackingRepository.findFirstByCourierIdOrderByTimeDesc("courier1")).thenReturn(Optional.of(lastTracking));

        CourierTracking courierTracking = new CourierTracking();
        when(courierTrackingMapper.mapCourierTrackingFrom(courierTrackingDto)).thenReturn(courierTracking);
        when(distanceCalculatorService.findNearStoresWithinRadius(anyInt(), anyDouble(), anyDouble())).thenReturn(List.of());

        //when
        courierTrackingService.save(courierTrackingDto);

        //then
        verify(courierTrackingRepository, times(1)).save(courierTracking);
        verify(courierTotalDistanceService, times(1)).saveUpdatedTotalDistance(courierTrackingDto, lastTracking);
        verify(distanceCalculatorService, times(1)).findNearStoresWithinRadius(100, 10.0, 20.0);
    }

    @Test
    void testSave_WithNearestStores() {
        //given
        CourierTrackingDto courierTrackingDto = new CourierTrackingDto();
        courierTrackingDto.setCourier("courier1");
        courierTrackingDto.setLat(10.0);
        courierTrackingDto.setLng(20.0);

        Store store1 = new Store();
        store1.setName("Store1");

        when(distanceCalculatorService.findNearStoresWithinRadius(anyInt(), anyDouble(), anyDouble())).thenReturn(List.of(store1));

        when(courierNearestStoreCacheService.findBy("courier1", "Store1")).thenReturn(Optional.empty());

        CourierTracking courierTracking = new CourierTracking();
        when(courierTrackingMapper.mapCourierTrackingFrom(courierTrackingDto)).thenReturn(courierTracking);

        //when
        courierTrackingService.save(courierTrackingDto);

        //then
        verify(courierNearestStoreCacheService, times(1)).save(any(CourierNearestStore.class));
    }
}
