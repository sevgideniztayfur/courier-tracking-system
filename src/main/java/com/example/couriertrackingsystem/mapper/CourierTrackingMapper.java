package com.example.couriertrackingsystem.mapper;

import com.example.couriertrackingsystem.model.CourierTracking;
import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import org.springframework.stereotype.Component;

@Component
public class CourierTrackingMapper {

    public CourierTracking mapCourierTrackingFrom(CourierTrackingDto courierTrackingDto){

        return CourierTracking.builder().
                courierId(courierTrackingDto.getCourier()).
                time(courierTrackingDto.getTime()).
                latitude(courierTrackingDto.getLat()).
                longitude(courierTrackingDto.getLng()).
                build();

    }
}
