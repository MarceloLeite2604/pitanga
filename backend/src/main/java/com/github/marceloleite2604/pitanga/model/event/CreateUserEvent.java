package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Builder;

public class CreateUserEvent extends Event<UserDao> {

    public CreateUserEvent() {
        this(null);
    }

    @Builder
    public CreateUserEvent(UserDao userDao) {
        super(EventType.CREATE_USER, userDao);
    }
}
