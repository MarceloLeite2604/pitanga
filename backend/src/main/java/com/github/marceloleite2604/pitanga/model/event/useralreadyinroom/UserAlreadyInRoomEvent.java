package com.github.marceloleite2604.pitanga.model.event.useralreadyinroom;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class UserAlreadyInRoomEvent extends Event<UserAlreadyInRoomPayload> {

    public UserAlreadyInRoomEvent() {
        this(null);
    }

    @Builder
    public UserAlreadyInRoomEvent(UserAlreadyInRoomPayload payload) {
        super(EventType.JOIN_USER, payload);
    }

}
