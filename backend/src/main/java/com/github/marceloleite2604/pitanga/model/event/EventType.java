package com.github.marceloleite2604.pitanga.model.event;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExists;
import com.github.marceloleite2604.pitanga.model.event.joinuser.JoinUserEvent;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedEvent;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

@Getter
public enum EventType {
    CREATE_USER(Values.CREATE_USER, CreateUserEvent.class),
    USER_CREATED(Values.USER_CREATED, UserCreatedEvent.class),
    MAX_USERS_REACHED(Values.MAX_USERS_REACHED, MaxUsersReachedEvent.class),
    JOIN_USER(Values.JOIN_USER, JoinUserEvent.class),
    USER_JOINED(Values.USER_JOINED, UserJoinedEvent.class),
    MAX_ROOM_USERS_REACHED(Values.MAX_ROOM_USERS_REACHED, MaxRoomsUsersReachedEvent.class),
    CREATE_ROOM(Values.CREATE_ROOM, CreateRoomEvent.class),
    ROOM_CREATED(Values.ROOM_CREATED, RoomCreatedEvent.class),
    MAX_ROOMS_REACHED(Values.MAX_ROOMS_REACHED, MaxRoomsReachedEvent.class),
    CHECK_ROOM_EXISTS(Values.CHECK_ROOM_EXISTS, CheckRoomExists.class);

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

    @Override
    public String toString() {
        return value;
    }

    @UtilityClass
    public class Values {
        public static final String CREATE_USER = "create-user";
        public static final String USER_CREATED = "user-created";
        public static final String MAX_USERS_REACHED = "max-users-reached";
        public static final String JOIN_USER = "join-user";
        public static final String USER_JOINED = "user-joined";
        public static final String MAX_ROOM_USERS_REACHED = "max-room-users-reached";
        public static final String CREATE_ROOM = "create-room";
        public static final String ROOM_CREATED = "room-created";
        public static final String MAX_ROOMS_REACHED = "max-rooms-reached";
        public static final String CHECK_ROOM_EXISTS = "check-room-exists";
    }
}
