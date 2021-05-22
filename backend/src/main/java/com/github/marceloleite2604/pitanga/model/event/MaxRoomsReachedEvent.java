package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.event.EmptyPayload;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class MaxRoomsReachedEvent extends Event<EmptyPayload> {

    @Builder
    public MaxRoomsReachedEvent() {
        super(EventType.MAX_ROOMS_REACHED, null);
    }
}
