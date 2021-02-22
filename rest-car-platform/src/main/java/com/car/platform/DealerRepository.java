package com.car.platform;

import org.springframework.data.jpa.repository.JpaRepository;

interface DealerRepository extends JpaRepository<Dealer, Long> {

    Dealer findByName(String name);

}
