package com.example.couriertrackingsystem.mapper;

import com.example.couriertrackingsystem.model.CourierTotalDistance;
import com.example.couriertrackingsystem.model.dto.CourierTotalDistanceDto;
import org.springframework.stereotype.Component;

@Component
public class CourierTotalDistanceMapper {

    public CourierTotalDistance mapCourierTrackingFrom(CourierTotalDistanceDto courierTotalDistanceDto){

        return CourierTotalDistance.builder().
                courierId(courierTotalDistanceDto.getCourierId()).
                distance(courierTotalDistanceDto.getDistance()).
                startTime(courierTotalDistanceDto.getStartTime()).
                updateTime(courierTotalDistanceDto.getUpdateTime()).
                build();

    }
}
