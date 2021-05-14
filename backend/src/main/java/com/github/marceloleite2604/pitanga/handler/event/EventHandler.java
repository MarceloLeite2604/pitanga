package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.IncomingContext;

public interface EventHandler {

    @SuppressWarnings("squid:S1452")
    Event<?> handle(IncomingContext incomingContext);

    void setNext(EventHandler eventHandler);
}
