package com.example.couriertrackingsystem.service;

import com.example.couriertrackingsystem.model.Store;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceCalculatorService {

    private final StoreService storeService;

    public DistanceCalculatorService(StoreService storeService) {
        this.storeService = storeService;
    }

    public List<Store> findNearStoresWithinRadius(int radius, double latitude, double longitude) {

        List<Store> allStore = storeService.getAll();
        List<Store> nearStores = new ArrayList<>();
        for (Store store: allStore) {
            if(isWithinRadius(radius, latitude, longitude, store)) {
                nearStores.add(store);
            }
        }
        return nearStores;
    }

    private boolean isWithinRadius(int radius, double latitude, double longitude, Store store) {
        double distanceToStore = calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude());
        return (distanceToStore <= radius);
    }

    public double calculateDistance(double fromLat, double fromLng, double toLat, double toLng) {

        final int EARTH_RADIUS = 6371000; //in meters

        double fromLatRad = Math.toRadians(fromLat);
        double fromLngRad = Math.toRadians(fromLng);
        double toLatRad = Math.toRadians(toLat);
        double toLngRad = Math.toRadians(toLng);

        double deltaLat = toLatRad - fromLatRad;
        double deltaLng = toLngRad - fromLngRad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(fromLatRad) * Math.cos(toLatRad)
                * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
