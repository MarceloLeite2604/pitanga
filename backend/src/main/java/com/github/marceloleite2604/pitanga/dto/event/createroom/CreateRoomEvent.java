package com.github.marceloleite2604.pitanga.dto.event.createroom;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
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
