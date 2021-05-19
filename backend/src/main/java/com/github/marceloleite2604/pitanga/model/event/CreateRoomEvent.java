package com.github.marceloleite2604.pitanga.model.event;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Builder;

public class CreateRoomEvent extends Event<UserDao> {

    public CreateRoomEvent() {
        this(null);
    }

    @Builder
    public CreateRoomEvent(UserDao payload) {
        super(EventType.CREATE_ROOM, payload);
    }
}
