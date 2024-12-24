package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.exception.BaseException;
import com.example.couriertrackingsystem.mapper.CourierTotalDistanceMapper;
import com.example.couriertrackingsystem.model.CourierTotalDistance;
import com.example.couriertrackingsystem.model.CourierTracking;
import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.model.response.TotalTravelDistanceResponse;
import com.example.couriertrackingsystem.repository.CourierTotalDistanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CourierTotalDistanceService {

    private final CourierTotalDistanceRepository courierTotalDistanceRepository;
    private final CourierTotalDistanceMapper courierTotalDistanceMapper;
    private final DistanceCalculatorService distanceCalculatorService;

    public CourierTotalDistanceService(CourierTotalDistanceRepository courierTotalDistanceRepository, CourierTotalDistanceMapper courierTotalDistanceMapper, DistanceCalculatorService distanceCalculatorService) {
        this.courierTotalDistanceRepository = courierTotalDistanceRepository;
        this.courierTotalDistanceMapper = courierTotalDistanceMapper;
        this.distanceCalculatorService = distanceCalculatorService;
    }

    public void saveInitialTotalDistance(CourierTrackingDto courierTrackingDto) {
        CourierTotalDistance courierTotalDistance = CourierTotalDistance.builder()
                .courierId(courierTrackingDto.getCourier())
                .distance(0.0)
                .startTime(courierTrackingDto.getTime())
                .updateTime(LocalDateTime.now())
                .build();

        courierTotalDistanceRepository.save(courierTotalDistance);
    }

    public void saveUpdatedTotalDistance(CourierTrackingDto courierTrackingDto, CourierTracking courierTracking) {
        double distance = distanceCalculatorService.calculateDistance(
                courierTracking.getLatitude(),
                courierTracking.getLongitude(),
                courierTrackingDto.getLat(),
                courierTrackingDto.getLng()
        );
        courierTotalDistanceRepository.findByCourierId(courierTrackingDto.getCourier())
                .ifPresentOrElse(
                        courierTotalDistance -> {
                            courierTotalDistance.setDistance(courierTotalDistance.getDistance() + distance);
                            courierTotalDistanceRepository.save(courierTotalDistance);
                        },
                        () -> {
                            log.error("ERROR! Cannot find total distance info for courier with id {}", courierTrackingDto.getCourier());
                            throw new BaseException("ERROR! Cannot find total distance info for courier with id " + courierTrackingDto.getCourier(), HttpStatus.NOT_FOUND);
                        });
    }

    public TotalTravelDistanceResponse getTotalDistance(String courierId) {
        return courierTotalDistanceRepository.findByCourierId(courierId)
                .map(entity -> TotalTravelDistanceResponse
                                    .builder()
                                    .totalDistance(entity.getDistance())
                                    .build())
                .orElseThrow(() -> {
                    log.error("ERROR! Cannot find total distance info for courier with id {}", courierId);
                    throw new BaseException("ERROR! Cannot find total distance info for courier with id " + courierId, HttpStatus.NOT_FOUND);
                });
    }
}
