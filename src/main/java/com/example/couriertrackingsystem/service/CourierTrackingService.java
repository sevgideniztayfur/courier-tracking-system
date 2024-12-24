package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.mapper.CourierTrackingMapper;
import com.example.couriertrackingsystem.model.CourierNearestStore;
import com.example.couriertrackingsystem.model.CourierTracking;
import com.example.couriertrackingsystem.model.Store;
import com.example.couriertrackingsystem.model.dto.CourierTrackingDto;
import com.example.couriertrackingsystem.repository.CourierTrackingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourierTrackingService {

    private final CourierTrackingRepository courierTrackingRepository;
    private final CourierTrackingMapper courierTrackingMapper;
    private final CourierTotalDistanceService courierTotalDistanceService;
    private final DistanceCalculatorService distanceCalculatorService;
    private final CourierNearestStoreCacheService courierNearestStoreCacheService;
    private final StoreService storeService;

    public CourierTrackingService(CourierTrackingRepository courierTrackingRepository, CourierTrackingMapper courierTrackingMapper, CourierTotalDistanceService courierTotalDistanceService, DistanceCalculatorService distanceCalculatorService, CourierNearestStoreCacheService courierNearestStoreCacheService, StoreService storeService) {
        this.courierTrackingRepository = courierTrackingRepository;
        this.courierTrackingMapper = courierTrackingMapper;
        this.courierTotalDistanceService = courierTotalDistanceService;
        this.distanceCalculatorService = distanceCalculatorService;
        this.courierNearestStoreCacheService = courierNearestStoreCacheService;
        this.storeService = storeService;
    }

    @Transactional
    public void save(CourierTrackingDto courierTrackingDto) {

        saveCourierTotalDistance(courierTrackingDto);
        saveCourierTracking(courierTrackingDto);

        //get nearest stores for courier
        List<Store> nearestStoresForCourier = getNearestStoresForCourier(courierTrackingDto);
        if(nearestStoresForCourier.isEmpty())
            return;

        //get already notified same store for courier from cache
        List<CourierNearestStore> alreadyNotifiedStoresForCourierFromCache = alreadyNotifiedSameStoresForCourierFromCache(nearestStoresForCourier, courierTrackingDto);

        //find nearest store for courier that not in cache
        List<Store> nearestStoreForCourierThatNotInCache = findNearestStoreForCourierThatNotInCache(nearestStoresForCourier, alreadyNotifiedStoresForCourierFromCache);

        //notify nearest store for courier that not in cache
        if(nearestStoreForCourierThatNotInCache.isEmpty())
            return;
        notifyNearestStoreForCourier(nearestStoreForCourierThatNotInCache, courierTrackingDto);
    }

    private void saveCourierTotalDistance(CourierTrackingDto courierTrackingDto){
        courierTrackingRepository.findFirstByCourierIdOrderByTimeDesc(courierTrackingDto.getCourier())
                .ifPresentOrElse(
                        courierTracking -> {
                            courierTotalDistanceService.saveUpdatedTotalDistance(courierTrackingDto, courierTracking);
                        },
                        () -> {
                            courierTotalDistanceService.saveInitialTotalDistance(courierTrackingDto);
                        });
    }

    private void saveCourierTracking(CourierTrackingDto courierTrackingDto){
        CourierTracking courierTracking = courierTrackingMapper.mapCourierTrackingFrom(courierTrackingDto);
        courierTrackingRepository.save(courierTracking);
    }

    private List<Store> getNearestStoresForCourier(CourierTrackingDto courierTrackingDto){
       return distanceCalculatorService.findNearStoresWithinRadius(100, courierTrackingDto.getLat(), courierTrackingDto.getLng());
    }

    private List<CourierNearestStore> alreadyNotifiedSameStoresForCourierFromCache(List<Store> nearestStoresForCourier, CourierTrackingDto courierTrackingDto){
        return nearestStoresForCourier
                .stream()
                .map(store -> courierNearestStoreCacheService.findBy(courierTrackingDto.getCourier(), store.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<Store> findNearestStoreForCourierThatNotInCache(List<Store> nearestStoresForCourier, List<CourierNearestStore> alreadyNotifiedStoresForCourierFromCache){
        return nearestStoresForCourier
                .stream()
                .filter(store -> alreadyNotifiedStoresForCourierFromCache
                        .stream()
                        .noneMatch(courierNearestStore -> courierNearestStore.getStoreName().equals(store.getName())))
                .collect(Collectors.toList());
    }

    private void notifyNearestStoreForCourier(List<Store> storeList, CourierTrackingDto courierTrackingDto){
        for (Store s: storeList) {
            CourierNearestStore newNearestStore = CourierNearestStore.builder()
                    .storeName(s.getName())
                    .courierId(courierTrackingDto.getCourier())
                    .build();
            log.info("[NOTIFY] -> Courier {} is closer under than 100 meters to the {} store at {}.", newNearestStore.getCourierId(), newNearestStore.getStoreName(), courierTrackingDto.getTime().toString());
            courierNearestStoreCacheService.save(newNearestStore);
        }
    }
}
