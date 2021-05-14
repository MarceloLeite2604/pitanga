package com.github.marceloleite2604.pitanga.model.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinUserEvent.class, name = "join-user"),
        @JsonSubTypes.Type(value = CreateRoomEvent.class, name = "create-room")}
)
@NoArgsConstructor
public class Event<T> {
    private EventType type;

    private T payload;
}
