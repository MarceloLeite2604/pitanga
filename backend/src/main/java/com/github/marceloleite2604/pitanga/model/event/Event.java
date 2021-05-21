package com.github.marceloleite2604.pitanga.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExistsEvent;
import com.github.marceloleite2604.pitanga.model.event.joinuser.JoinUserEvent;
import com.github.marceloleite2604.pitanga.model.event.userjoined.UserJoinedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateUserEvent.class, name = EventType.Values.CREATE_USER),
        @JsonSubTypes.Type(value = UserCreatedEvent.class, name = EventType.Values.USER_CREATED),
        @JsonSubTypes.Type(value = MaxUsersReachedEvent.class, name = EventType.Values.MAX_USERS_REACHED),
        @JsonSubTypes.Type(value = JoinUserEvent.class, name = EventType.Values.JOIN_USER),
        @JsonSubTypes.Type(value = UserJoinedEvent.class, name = EventType.Values.USER_JOINED),
        @JsonSubTypes.Type(value = MaxRoomsUsersReachedEvent.class, name = EventType.Values.MAX_ROOM_USERS_REACHED),
        @JsonSubTypes.Type(value = CreateRoomEvent.class, name = EventType.Values.CREATE_ROOM),
        @JsonSubTypes.Type(value = RoomCreatedEvent.class, name = EventType.Values.ROOM_CREATED),
        @JsonSubTypes.Type(value = MaxRoomsReachedEvent.class, name = EventType.Values.MAX_ROOMS_REACHED),
        @JsonSubTypes.Type(value = CheckRoomExistsEvent.class, name = EventType.Values.CHECK_ROOM_EXISTS),
        @JsonSubTypes.Type(value = UserDroppedEvent.class, name = EventType.Values.USER_DROPPED)
})
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event<T> {

    @JsonIgnore
    private EventType type;

    private T payload;
}
