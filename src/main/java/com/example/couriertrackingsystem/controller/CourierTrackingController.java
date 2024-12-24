package com.example.couriertrackingsystem.controller;

import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.model.response.TotalTravelDistanceResponse;
import com.example.couriertrackingsystem.service.CourierTotalDistanceService;
import com.example.couriertrackingsystem.service.CourierTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/couriertrackings")
public class CourierTrackingController {

    private final CourierTrackingService courierTrackingService;
    private final CourierTotalDistanceService courierTotalDistanceService;

    public CourierTrackingController(CourierTrackingService courierTrackingService, CourierTotalDistanceService courierTotalDistanceService) {
        this.courierTrackingService = courierTrackingService;
        this.courierTotalDistanceService = courierTotalDistanceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveCourierTracking(@RequestBody CourierTrackingDto courierTrackingDto) {

        log.info("[INFO] -> New Courier Tracking: {} ", courierTrackingDto.toString());
        courierTrackingService.save(courierTrackingDto);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{courierId}/totalTravelDistance", method = RequestMethod.GET)
    public ResponseEntity<TotalTravelDistanceResponse> getTotalTravelDistance(@PathVariable("courierId") String courierId) {

        TotalTravelDistanceResponse response = courierTotalDistanceService.getTotalDistance(courierId);
        return ResponseEntity.ok(response);
    }
}
