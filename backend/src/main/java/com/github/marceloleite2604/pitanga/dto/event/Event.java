package com.github.marceloleite2604.pitanga.dto.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.marceloleite2604.pitanga.dto.event.checkroomexists.CheckRoomExistsEvent;
import com.github.marceloleite2604.pitanga.dto.event.createroom.CreateRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.createuser.CreateUserEvent;
import com.github.marceloleite2604.pitanga.dto.event.joinuser.JoinUserEvent;
import com.github.marceloleite2604.pitanga.dto.event.resetroom.ResetRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.roomcreated.RoomCreatedEvent;
import com.github.marceloleite2604.pitanga.dto.event.usercreated.UserCreatedEvent;
import com.github.marceloleite2604.pitanga.dto.event.userjoined.UserJoinedEvent;
import com.github.marceloleite2604.pitanga.dto.event.attendeevoted.AttendeeVotedEvent;
import com.github.marceloleite2604.pitanga.dto.event.useralreadyinroom.UserAlreadyInRoomEvent;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.dto.event.attendeeleftroom.AttendeeLeftRoomEvent;
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
        @JsonSubTypes.Type(value = UserAlreadyInRoomEvent.class, name = EventType.Values.USER_ALREADY_IN_ROOM),
        @JsonSubTypes.Type(value = MaxRoomsUsersReachedEvent.class, name = EventType.Values.MAX_ROOM_USERS_REACHED),
        @JsonSubTypes.Type(value = CreateRoomEvent.class, name = EventType.Values.CREATE_ROOM),
        @JsonSubTypes.Type(value = RoomCreatedEvent.class, name = EventType.Values.ROOM_CREATED),
        @JsonSubTypes.Type(value = MaxRoomsReachedEvent.class, name = EventType.Values.MAX_ROOMS_REACHED),
        @JsonSubTypes.Type(value = CheckRoomExistsEvent.class, name = EventType.Values.CHECK_ROOM_EXISTS),
        @JsonSubTypes.Type(value = UserDroppedEvent.class, name = EventType.Values.USER_DROPPED),
        @JsonSubTypes.Type(value = AttendeeVotedEvent.class, name = EventType.Values.ATTENDEE_VOTED),
        @JsonSubTypes.Type(value = RevealVotesEvent.class, name = EventType.Values.REVEAL_VOTES),
        @JsonSubTypes.Type(value = ResetRoomEvent.class, name = EventType.Values.RESET_ROOM),
        @JsonSubTypes.Type(value = AttendeeLeftRoomEvent.class, name = EventType.Values.ATTENDEE_LEFT_ROOM)
})
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event<T extends Payload> {

    @JsonIgnore
    private EventType type;

    private T payload;
}
