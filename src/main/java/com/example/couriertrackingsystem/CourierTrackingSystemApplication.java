package com.example.couriertrackingsystem;

import com.example.couriertrackingsystem.provider.StoreProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourierTrackingSystemApplication implements CommandLineRunner {

    private final StoreProvider storeProvider;


    public CourierTrackingSystemApplication(StoreProvider storeProvider) {
        this.storeProvider = storeProvider;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourierTrackingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        storeProvider.saveStores();
    }

}
