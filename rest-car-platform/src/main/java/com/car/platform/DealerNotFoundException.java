package com.car.platform;

class DealerNotFoundException extends RuntimeException {

    DealerNotFoundException(Long id) {
        super("Could not find dealer " + id);
    }
}
