package com.github.marceloleite2604.pitanga.model;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum OutgoingEventType {
    ROOM_CREATED("room-created", Room.class);

    private final String value;

    private final Class<?> payloadClass;

    OutgoingEventType(String value, Class<?> payloadClass) {
        this.value = value;
        this.payloadClass = payloadClass;
    }

    OutgoingEventType(String value) {
        this(value, null);
    }

    public static OutgoingEventType findByValue(String value) {

        if (ObjectUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Value is empty.");
        }

        for (OutgoingEventType outgoingEventType : values()) {
            if (value.equals(outgoingEventType.value)) {
                return outgoingEventType;
            }
        }

        throw new IllegalArgumentException(String.format("Unrecognized value \"%s\".", value));
    }
}
