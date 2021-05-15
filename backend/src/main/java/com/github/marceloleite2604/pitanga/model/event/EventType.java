package com.github.marceloleite2604.pitanga.model.event;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExists;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum EventType {
    JOIN_USER("join-user", JoinUserEvent.class),
    USER_JOINED("user-joined", UserJoinedEvent.class),
    ROOM_CREATED("room-created", RoomCreatedEvent.class),
    CREATE_ROOM("create-room", CreateRoomEvent.class),
    CHECK_ROOM_EXISTS("check-room-exists", CheckRoomExists.class);

    @JsonValue
    private final String value;

    private final Class<? extends Event<?>> eventClass;

    EventType(String value, Class<? extends Event<?>> eventClass) {
        this.value = value;
        this.eventClass = eventClass;
    }

    public static EventType findByValue(String value) {

        if (ObjectUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Value is empty.");
        }

        for (EventType eventType : values()) {
            if (value.equals(eventType.value)) {
                return eventType;
            }
        }

        throw new IllegalArgumentException(String.format("Unrecognized value \"%s\".", value));
    }
}
