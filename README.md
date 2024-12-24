
# Courier Tracking

In this project, a rest application was written for courier tracking. With the location information of the couriers, it is possible to learn which store the courier is approaching and the total distance traveled.

## Requirements

* Java 17
* Maven
* Avaible port on 8080

## Tech Stack

* H2 Database
* Spring Boot
* Swagger
* Junit
* Caffeine In Memory Cache

## Used Design Patterns

* Singleton -> Singleton design pattern is used with @Component annotation in the spring boot. In addition, constructer injection used to provide being singleton of instances.
* Builder -> Builder design pattern makes the process of creating complex objects more readable and manageable, allowing you to structure the different components of the object step by step.
* Proxy -> Proxy design pattern provides an intermediary object that acts as a substitute for an object to control access to that object or extend its functionality. In this project, @Transactional and @Cache_Put annotations have been activated thanks to proxy pattern.

## General Flow

* Firstly, when the project runs, the stores from "stores.json" where are located in resources folder are saved both H2 database table(Store) and in memory cache.
* You can check controller with Swagger (http://localhost:8080/swagger-ui/index.html)
* Then, the use cases can be triggered on REST endpoints as seen below
  
    - /couriertrackings (POST)
      - This endpoint provides to save both courier tracking detail(Courier_Tracking) and courier total distance(Courier_Total_Distance)  to database.
      - After that, checks if the courier gets closer to any store within 100 meter radius or not
      - If the courier not close to any store, operation finishes
      - If the courier close to any store first time in last 1 minute, the log is published to console in order to show related courier id, store data and time of request with [NOTIFY] marker.
      - If the courier has already within any store 100 meter in last 1 minute, any log published to console.
      - Besides that, every request info published to console with [INFO] marker. For easify to follow.
      
    - /couriertrackings/{courierId}/totalTravelDistance (GET)
      - This endpoint provides to get total travel distance of courier.
      - The operation only retrieves the relevant record from the database.
      - Since, when recording courier information at the /couriertrackings endpoint; the courier's previous path distance and the new travel distance are collected and recorded in a separate table(Courier_Total_Distance).
        
## How to run it

* Build project and run (http://localhost:8080/swagger-ui/index.html)
* For db access go to http://localhost:8080/h2-console.
  - Username and password information is available in the application.properties file.
