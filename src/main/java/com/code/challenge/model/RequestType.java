package com.code.challenge.model;

import java.util.EnumSet;

public enum RequestType {
    USER_SINGERS(1),
    USER_GENRES(2),
    SINGER_AGE(3),
    GENRE_AGE(4),
    SINGER_CITY(5),
    GENRE_CITY(6);

    private final int value;

    RequestType(int value) {
        this.value = value;
    }

    public static RequestType fromValue(int value) {
        return EnumSet.allOf(RequestType.class)
                .stream()
                .filter(types -> types.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not enum with type :" + value));
    }
}
