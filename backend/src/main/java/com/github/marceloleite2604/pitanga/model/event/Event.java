package com.github.marceloleite2604.pitanga.model.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.marceloleite2604.pitanga.model.event.checkroomexists.CheckRoomExists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinUserEvent.class, name = "join-user"),
        @JsonSubTypes.Type(value = CreateRoomEvent.class, name = "create-room"),
        @JsonSubTypes.Type(value = CheckRoomExists.class, name = "check-room-exists")}
)
@NoArgsConstructor
public class Event<T> {
    private EventType type;

    private T payload;
}
