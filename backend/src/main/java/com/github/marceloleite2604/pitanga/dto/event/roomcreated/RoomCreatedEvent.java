package com.github.marceloleite2604.pitanga.dto.event.roomcreated;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class RoomCreatedEvent extends Event<RoomCreatedPayload> {

    public RoomCreatedEvent() {
        this(null);
    }

    @Builder
    public RoomCreatedEvent(RoomCreatedPayload payload) {
        super(EventType.ROOM_CREATED, payload);
    }
}
