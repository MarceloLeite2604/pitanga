package com.github.marceloleite2604.pitanga.dto.event.userdropped;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class UserDroppedEvent extends Event<UserDroppedPayload> {

    public UserDroppedEvent() {
        this(null);
    }

    @Builder
    public UserDroppedEvent(UserDroppedPayload payload) {
        super(EventType.USER_DROPPED, payload);
    }
}
