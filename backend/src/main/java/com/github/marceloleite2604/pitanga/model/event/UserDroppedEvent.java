package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Builder;

public class UserDroppedEvent extends Event<UserDao> {

    public UserDroppedEvent() {
        this(null);
    }

    @Builder
    public UserDroppedEvent(UserDao payload) {
        super(EventType.USER_DROPPED, payload);
    }
}
