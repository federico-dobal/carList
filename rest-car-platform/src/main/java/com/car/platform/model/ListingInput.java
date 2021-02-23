package com.car.platform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

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

    public String getCode() {
        return code;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getkW() {
        return kW;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Long getPrice() {
        return price;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setkW(Integer kW) {
        this.kW = kW;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean isWellFormed() {
        return this.code != null && this.make != null && this.model != null &&
                this.kW != null && this.year != null && this.color != null && this.price != null;
    }
}
