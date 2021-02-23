package com.car.platform.exceptions;

public class DealerNotFoundException extends RuntimeException {

    public DealerNotFoundException(Long id) {
        super("Could not find dealer " + id);
    }
}
