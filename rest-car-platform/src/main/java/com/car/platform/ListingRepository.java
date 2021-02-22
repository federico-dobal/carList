package com.car.platform;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing findByCodeAndDealerId(String code, Long dealerId);

}
