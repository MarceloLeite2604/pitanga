package com.github.marceloleite2604.pitanga.model.event.userjoined;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import lombok.Builder;

public class UserJoinedEvent extends Event<UserJoinedPayload> {

    public UserJoinedEvent() {
        this(null);
    }

    @Builder
    public UserJoinedEvent(UserJoinedPayload payload) {
        super(EventType.USER_JOINED, payload);
    }
}
