package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;

public class UserJoinedEvent extends Event<User> {

    @Builder
    public UserJoinedEvent(User payload) {
        super(EventType.USER_JOINED, payload);
    }
}
