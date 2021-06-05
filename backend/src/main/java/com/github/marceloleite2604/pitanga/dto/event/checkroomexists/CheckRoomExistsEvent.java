package com.github.marceloleite2604.pitanga.dto.event.checkroomexists;

import com.github.marceloleite2604.pitanga.dto.event.EventType;
import com.github.marceloleite2604.pitanga.dto.event.Event;
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
