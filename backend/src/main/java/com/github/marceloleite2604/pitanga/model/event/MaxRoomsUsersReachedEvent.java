package com.github.marceloleite2604.pitanga.model.event;

import lombok.Builder;

public class MaxRoomsUsersReachedEvent extends Event<EmptyPayload> {

    @Builder
    public MaxRoomsUsersReachedEvent() {
        super(EventType.MAX_ROOM_USERS_REACHED, null);
    }
}
