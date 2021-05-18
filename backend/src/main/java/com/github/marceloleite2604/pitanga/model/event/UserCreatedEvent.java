package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;

public class UserCreatedEvent extends Event<User> {

    @Builder
    public UserCreatedEvent(User payload) {
        super(EventType.USER_CREATED, payload);
    }
}
