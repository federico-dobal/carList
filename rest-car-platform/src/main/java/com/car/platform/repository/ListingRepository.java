package com.car.platform.repository;

import com.car.platform.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    public Listing findByCodeAndDealerId(String code, Long dealerId);

}
