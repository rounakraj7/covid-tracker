package com.wander.covidtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        CovidTrackerApplication.class,
})
public class CovidTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerApplication.class, args);
    }

}
