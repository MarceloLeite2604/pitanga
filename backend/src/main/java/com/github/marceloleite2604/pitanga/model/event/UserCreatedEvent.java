package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Builder;

public class UserCreatedEvent extends Event<UserDao> {

    @Builder
    public UserCreatedEvent(UserDao payload) {
        super(EventType.USER_CREATED, payload);
    }
}
