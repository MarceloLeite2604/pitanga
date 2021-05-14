package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.incoming.IncomingContext;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEvent;

public interface EventHandler {

    OutgoingEvent<?> handle(IncomingContext incomingContext);

    void setNext(EventHandler eventHandler);
}
