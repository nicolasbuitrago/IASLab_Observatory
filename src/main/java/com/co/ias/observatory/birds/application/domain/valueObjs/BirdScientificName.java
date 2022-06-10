package com.co.ias.observatory.birds.application.domain.valueObjs;

import org.apache.commons.lang3.Validate;

public class BirdScientificName {

    private final String value;

    public BirdScientificName(String value) {
        Validate.notNull(value, "Bird scientific name can not be null.");
        Validate.isTrue(value.length() <= 30, "Bird scientific name can not be longer than 30 characters.");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
