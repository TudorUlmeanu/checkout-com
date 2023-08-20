package com.example.ckobanksimulator.objects;

public enum Status {
    AUTHORISED(1),
    SETTLED(2),
    REFUNDED(3),
    CANCELED(4),
    REFUSED(5);

    private final Integer value;

    Status(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
