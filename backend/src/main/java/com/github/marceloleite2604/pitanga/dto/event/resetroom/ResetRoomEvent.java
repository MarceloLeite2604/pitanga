package com.github.marceloleite2604.pitanga.dto.event.resetroom;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class ResetRoomEvent extends Event<ResetRoomPayload> {

    public ResetRoomEvent() {
        this(null);
    }

    @Builder
    public ResetRoomEvent(ResetRoomPayload payload) {
        super(EventType.RESET_ROOM, payload);
    }
}
