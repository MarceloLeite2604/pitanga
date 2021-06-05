package com.github.marceloleite2604.pitanga.dto.event;

import lombok.Builder;

public class MaxUsersReachedEvent extends Event<EmptyPayload> {

    @Builder
    public MaxUsersReachedEvent() {
        super(EventType.MAX_USERS_REACHED, null);
    }
}