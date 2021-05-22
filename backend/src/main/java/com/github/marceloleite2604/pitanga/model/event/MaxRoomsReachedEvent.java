package com.github.marceloleite2604.pitanga.model.event;

import lombok.Builder;

public class MaxRoomsReachedEvent extends Event<EmptyPayload> {

    @Builder
    public MaxRoomsReachedEvent() {
        super(EventType.MAX_ROOMS_REACHED, null);
    }
}
