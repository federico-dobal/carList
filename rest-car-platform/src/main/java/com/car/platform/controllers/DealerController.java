package com.car.platform.controllers;

import com.car.platform.entity.Dealer;
import com.car.platform.model.DealerInput;
import com.car.platform.exceptions.DealerNotFoundException;
import com.car.platform.repository.DealerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DealerController {

    private final DealerRepository repository;

    public DealerController(DealerRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all the dealers details
     * @return
     */
    @GetMapping("/dealers")
    List<Dealer> allDealers() {
        return repository.findAll();
    }

    /**
     * Insert/Update a dealer
     * @param dealer dealer to insert into the DB
     * @return
     */
    @PostMapping("/dealer")
    ResponseEntity<String> newDealer(@RequestBody DealerInput dealer) {
        Dealer dealerFromDb = repository.findByName(dealer.getName());
        // Only insert if it does not exists
        if (dealerFromDb == null) {
            Dealer newDealer = new Dealer(dealer.getName());
            repository.save(newDealer);
            return ResponseEntity.status(HttpStatus.OK).body("Dealer inserted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dealer cannot be inserted");
        }
    }

    /**
     * Get details associated with a given dealer
     * @param id dealer id
     * @return dealer detail
     */
    @GetMapping("/dealer/{id}")
    Dealer getSingleDealer(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DealerNotFoundException(id));
    }
}
