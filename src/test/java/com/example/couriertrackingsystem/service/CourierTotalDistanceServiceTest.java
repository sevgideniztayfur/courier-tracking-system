package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.exception.BaseException;
import com.example.couriertrackingsystem.model.CourierTotalDistance;
import com.example.couriertrackingsystem.model.CourierTracking;
import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.model.response.TotalTravelDistanceResponse;
import com.example.couriertrackingsystem.repository.CourierTotalDistanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourierTotalDistanceServiceTest {

    @InjectMocks
    private CourierTotalDistanceService courierTotalDistanceService;
    @Mock
    private CourierTotalDistanceRepository courierTotalDistanceRepository;
    @Mock
    private DistanceCalculatorService distanceCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInitialTotalDistance() {
        //given
        CourierTrackingDto dto = new CourierTrackingDto();
        dto.setCourier("courier1");
        dto.setTime(LocalDateTime.now());

        //when
        courierTotalDistanceService.saveInitialTotalDistance(dto);

        //then
        verify(courierTotalDistanceRepository, times(1)).save(any(CourierTotalDistance.class));
    }

    @Test
    void testSaveUpdatedTotalDistance_Success() {
        //given
        CourierTrackingDto dto = new CourierTrackingDto();
        dto.setCourier("courier1");
        dto.setLat(40.0);
        dto.setLng(29.0);

        CourierTracking tracking = new CourierTracking();
        tracking.setLatitude(41.0);
        tracking.setLongitude(30.0);

        CourierTotalDistance existing = new CourierTotalDistance();
        existing.setCourierId("courier1");
        existing.setDistance(100.0);

        when(courierTotalDistanceRepository.findByCourierId("courier1")).thenReturn(Optional.of(existing));
        when(distanceCalculatorService.calculateDistance(41.0, 30.0, 40.0, 29.0)).thenReturn(150.0);

        //when
        courierTotalDistanceService.saveUpdatedTotalDistance(dto, tracking);

        //then
        verify(courierTotalDistanceRepository, times(1)).save(existing);
        assertEquals(250.0, existing.getDistance());
    }

    @Test
    void testSaveUpdatedTotalDistance_ThrowsException() {
        //given
        CourierTrackingDto dto = new CourierTrackingDto();
        dto.setCourier("courier1");
        dto.setLat(40.0);
        dto.setLng(29.0);

        CourierTracking tracking = new CourierTracking();
        tracking.setLatitude(41.0);
        tracking.setLongitude(30.0);

        when(courierTotalDistanceRepository.findByCourierId("courier1")).thenReturn(Optional.empty());

        //when & then
        assertThrows(BaseException.class, () ->
                courierTotalDistanceService.saveUpdatedTotalDistance(dto, tracking));
    }

    @Test
    void testGetTotalDistance_Success() {
        //given
        String courierId = "courier1";
        CourierTotalDistance existing = new CourierTotalDistance();
        existing.setCourierId(courierId);
        existing.setDistance(200.0);

        when(courierTotalDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(existing));

        //when
        TotalTravelDistanceResponse response = courierTotalDistanceService.getTotalDistance(courierId);

        //then
        assertEquals(200.0, response.getTotalDistance());
    }

    @Test
    void testGetTotalDistance_ThrowsException() {
        //given
        String courierId = "courier1";
        when(courierTotalDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(BaseException.class, () ->
                courierTotalDistanceService.getTotalDistance(courierId));
    }
}
