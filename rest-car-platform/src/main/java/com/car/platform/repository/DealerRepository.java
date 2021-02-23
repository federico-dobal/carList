package com.car.platform.repository;

import com.car.platform.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {

    public Dealer findByName(String name);

}
