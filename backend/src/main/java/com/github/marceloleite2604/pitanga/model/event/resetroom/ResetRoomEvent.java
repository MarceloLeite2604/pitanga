package com.github.marceloleite2604.pitanga.model.event.resetroom;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
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
