package com.car.platform.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ListingInput {

    private String code;
    private String make;
    private String model;
    private Integer kW;
    private Integer year;
    private String color;
    private Long price;


    ListingInput() {}

    public ListingInput(String code, String make, String model, Integer kW, Integer year, String color, Long price) {
        this.code = code;
        this.make = make;
        this.model = model;
        this.kW = kW;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    public Boolean isWellFormed() {
        return this.code != null && this.make != null && this.model != null &&
                this.kW != null && this.year != null && this.color != null && this.price != null;
    }
}
