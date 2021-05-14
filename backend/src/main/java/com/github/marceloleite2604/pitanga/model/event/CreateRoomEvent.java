package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;

public class CreateRoomEvent extends Event<User> {

    public CreateRoomEvent() {
        this(null);
    }

    @Builder
    public CreateRoomEvent(User payload) {
        super(EventType.CREATE_ROOM, payload);
    }
}
