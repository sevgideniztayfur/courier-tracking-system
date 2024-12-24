package com.example.couriertrackingsystem.controller;

import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.model.response.TotalTravelDistanceResponse;
import com.example.couriertrackingsystem.service.CourierTotalDistanceService;
import com.example.couriertrackingsystem.service.CourierTrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourierTrackingControllerTest {

    @InjectMocks
    private CourierTrackingController courierTrackingController;
    @Mock
    private CourierTrackingService courierTrackingService;
    @Mock
    private CourierTotalDistanceService courierTotalDistanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourierTracking_ShouldCallServiceAndReturnNoContent() {
        //given
        CourierTrackingDto courierTrackingDto = new CourierTrackingDto();
        doNothing().when(courierTrackingService).save(courierTrackingDto);

        //when
        ResponseEntity<Void> response = courierTrackingController.saveCourierTracking(courierTrackingDto);

        //then
        verify(courierTrackingService, times(1)).save(courierTrackingDto);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void getTotalTravelDistance_ShouldReturnTotalTravelDistanceResponse() {
        //given
        String courierId = "courier123";
        TotalTravelDistanceResponse expectedResponse = TotalTravelDistanceResponse.builder()
                .totalDistance(120.5)
                .build();

        when(courierTotalDistanceService.getTotalDistance(courierId)).thenReturn(expectedResponse);

        //when
        ResponseEntity<TotalTravelDistanceResponse> response = courierTrackingController.getTotalTravelDistance(courierId);

        //then
        verify(courierTotalDistanceService, times(1)).getTotalDistance(courierId);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
    }
}
