package com.github.marceloleite2604.pitanga.model.event.roomcreated;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
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
