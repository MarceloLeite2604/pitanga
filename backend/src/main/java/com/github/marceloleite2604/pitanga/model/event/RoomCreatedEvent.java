package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.dao.RoomDao;
import lombok.Builder;

public class RoomCreatedEvent extends Event<RoomDao> {

    public RoomCreatedEvent() {
        this(null);
    }

    @Builder
    public RoomCreatedEvent(RoomDao payload) {
        super(EventType.ROOM_CREATED, payload);
    }
}
