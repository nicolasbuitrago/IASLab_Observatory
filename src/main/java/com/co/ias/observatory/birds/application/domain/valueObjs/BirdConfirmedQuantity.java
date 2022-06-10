package com.co.ias.observatory.birds.application.domain.valueObjs;

import org.apache.commons.lang3.Validate;

public class BirdConfirmedQuantity {

    private final Integer value;

    public BirdConfirmedQuantity(Integer value) {
        Validate.notNull(value, "Bird confirmed quantity can not be null.");
        Validate.inclusiveBetween(1,100_000, value, "Bird confirmed quantity must be between 1 and 100.000");
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
