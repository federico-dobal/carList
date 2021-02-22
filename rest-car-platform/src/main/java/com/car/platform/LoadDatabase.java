package com.car.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ListingRepository repository, DealerRepository dealerRepository) {

        return args -> {
            log.info("Preloading listing " + repository.save(new Listing(1L, "1", "mercedes", "a 180", 123, 2014, "black", 15950L)));
            log.info("Preloading listing " + repository.save(new Listing(1L,"a", "renault", "megane", 123, 2014, "red", 13990L)));
            log.info("Preloading dealer " + dealerRepository.save(new Dealer("dealer_1")));
        };
    }
}
