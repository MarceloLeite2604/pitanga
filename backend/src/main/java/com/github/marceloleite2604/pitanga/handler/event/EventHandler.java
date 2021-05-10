package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingEvent;
import com.github.marceloleite2604.pitanga.model.OutgoingEvent;

public interface EventHandler {

    OutgoingEvent<?> handle(IncomingEvent<?> incomingEvent);

    void setNext(EventHandler eventHandler);
}
