package com.github.marceloleite2604.pitanga.dto.event.usercreated;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class UserCreatedEvent extends Event<UserCreatedPayload> {

    public UserCreatedEvent() {
        this(null);
    }

    @Builder
    public UserCreatedEvent(UserCreatedPayload payload) {
        super(EventType.USER_CREATED, payload);
    }
}
