package com.car.platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingInput {

    private String code;
    private String make;
    private String model;
    private Integer kw;
    private Integer year;
    private String color;
    private Long price;


    ListingInput() {}

    public ListingInput(String code, String make, String model, Integer kw, Integer year, String color, Long price) {
        this.code = code;
        this.make = make;
        this.model = model;
        this.kw = kw;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    public Boolean isWellFormed() {
        return this.code != null && this.make != null && this.model != null &&
                this.kw != null && this.year != null && this.color != null && this.price != null;
    }
}
