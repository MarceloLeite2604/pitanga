package com.github.marceloleite2604.pitanga.model;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.Event;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncomingContext {

    private final Event<?> event;
    private final UserDao sender;
}
