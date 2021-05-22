package com.github.marceloleite2604.pitanga.model;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.Payload;
import lombok.Builder;

public record IncomingContext(
        Event<? extends Payload> event,
        UserDao sender) {

    @Builder
    public IncomingContext{}
}
