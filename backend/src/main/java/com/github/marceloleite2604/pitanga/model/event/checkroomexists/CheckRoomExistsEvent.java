package com.github.marceloleite2604.pitanga.model.event.checkroomexists;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class CheckRoomExistsEvent extends Event<CheckRoomExistsPayload> {

    public CheckRoomExistsEvent() {
        this(null);
    }

    @Builder
    public CheckRoomExistsEvent(CheckRoomExistsPayload payload) {
        super(EventType.CHECK_ROOM_EXISTS, payload);
    }
}
