package com.car.platform;

import java.util.Objects;

class DealerInput {

    private String name;

    DealerInput() {}

    public DealerInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealerInput that = (DealerInput) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "DealerInput{" +
                "name='" + name + '\'' +
                '}';
    }
}
