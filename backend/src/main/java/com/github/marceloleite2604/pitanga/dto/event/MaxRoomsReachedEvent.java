package com.github.marceloleite2604.pitanga.dto.event;

import lombok.Builder;

public class MaxRoomsReachedEvent extends Event<EmptyPayload> {

    @Builder
    public MaxRoomsReachedEvent() {
        super(EventType.MAX_ROOMS_REACHED, null);
    }
}
