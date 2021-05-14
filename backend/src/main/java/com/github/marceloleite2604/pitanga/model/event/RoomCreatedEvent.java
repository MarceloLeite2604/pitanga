package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.Room;
import lombok.Builder;

public class RoomCreatedEvent extends Event<Room> {

    @Builder
    public RoomCreatedEvent(Room payload) {
        super(EventType.ROOM_CREATED, payload);
    }
}
