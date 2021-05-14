package com.github.marceloleite2604.pitanga.model.incoming;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum IncomingEventType {
    JOIN_USER("join-user"),
    CREATE_ROOM("create-room", User.class);

    private final String value;

    private final Class<?> payloadClass;

    IncomingEventType(String value, Class<?> payloadClass) {
        this.value = value;
        this.payloadClass = payloadClass;
    }

    IncomingEventType(String value) {
        this(value, null);
    }

    public static IncomingEventType findByValue(String value) {

        if (ObjectUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Value is empty.");
        }

        for (IncomingEventType incomingEventType : values()) {
            if (value.equals(incomingEventType.value)) {
                return incomingEventType;
            }
        }

        throw new IllegalArgumentException(String.format("Unrecognized value \"%s\".", value));
    }
}
