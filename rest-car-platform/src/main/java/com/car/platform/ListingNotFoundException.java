package com.car.platform;

class ListingNotFoundException extends RuntimeException {

    ListingNotFoundException(Long id) {
        super("Could not find listing " + id);
    }
}
