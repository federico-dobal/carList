package com.car.platform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Listing implements Serializable {

    private @Id @GeneratedValue Long id;
    private Long dealerId;
    private String code;
    private String make;
    private String model;
    private Integer powerInPs;
    private Integer year;
    private String color;
    private Long price;


    public Listing() {}

    public Listing(Long dealerId, String code, String make, String model, Integer powerInPs, Integer year, String color, Long price) {
        this.dealerId = dealerId;
        this.code = code;
        this.make = make;
        this.model = model;
        this.powerInPs = powerInPs;
        this.year = year;
        this.color = color;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public Long getDealerId() {
        return dealerId;
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

    public Integer getPowerInPs() {
        return powerInPs;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
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

    public void setPowerInPs(Integer powerInPs) {
        this.powerInPs = powerInPs;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return Objects.equals(getId(), listing.getId()) &&
                Objects.equals(getDealerId(), listing.getDealerId()) &&
                Objects.equals(getCode(), listing.getCode()) &&
                Objects.equals(getMake(), listing.getMake()) &&
                Objects.equals(getModel(), listing.getModel()) &&
                Objects.equals(getPowerInPs(), listing.getPowerInPs()) &&
                Objects.equals(getYear(), listing.getYear()) &&
                Objects.equals(getColor(), listing.getColor()) &&
                Objects.equals(getPrice(), listing.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDealerId(), getCode(), getMake(), getModel(), getPowerInPs(), getYear(), getColor(), getPrice());
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", dealerId='" + dealerId + '\'' +
                ", code='" + code + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", powerInPs=" + powerInPs +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}
