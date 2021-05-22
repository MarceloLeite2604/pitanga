package com.github.marceloleite2604.pitanga.model.event.createroom;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class CreateRoomEvent extends Event<CreateRoomPayload> {

    public CreateRoomEvent() {
        this(null);
    }

    @Builder
    public CreateRoomEvent(CreateRoomPayload payload) {
        super(EventType.CREATE_ROOM, payload);
    }
}
