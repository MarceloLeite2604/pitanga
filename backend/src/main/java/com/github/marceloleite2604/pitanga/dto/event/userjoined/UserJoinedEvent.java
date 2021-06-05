package com.github.marceloleite2604.pitanga.dto.event.userjoined;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
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
