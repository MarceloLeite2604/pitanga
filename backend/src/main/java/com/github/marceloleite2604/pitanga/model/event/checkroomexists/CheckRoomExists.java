package com.github.marceloleite2604.pitanga.model.event.checkroomexists;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class CheckRoomExists extends Event<CheckRoomExistsPayload> {

    public CheckRoomExists() {
        this(null);
    }

    @Builder
    public CheckRoomExists(CheckRoomExistsPayload payload) {
        super(EventType.CHECK_ROOM_EXISTS, payload);
    }
}
