package com.github.marceloleite2604.pitanga.model.event.usercreated;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
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
