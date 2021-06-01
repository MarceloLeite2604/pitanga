package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EventHandler {

    @Transactional
    OutgoingContext handle(IncomingContext incomingContext);

    void setNext(EventHandler eventHandler);
}
