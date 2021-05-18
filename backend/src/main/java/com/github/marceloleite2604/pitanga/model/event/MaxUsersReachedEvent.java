package com.github.marceloleite2604.pitanga.model.event;

import lombok.Builder;

public class MaxUsersReachedEvent extends Event<Void> {

    @Builder
    public MaxUsersReachedEvent() {
        super(EventType.MAX_USERS_REACHED, null);
    }
}