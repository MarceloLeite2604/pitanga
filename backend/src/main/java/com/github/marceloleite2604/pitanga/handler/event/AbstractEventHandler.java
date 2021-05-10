package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingEvent;
import com.github.marceloleite2604.pitanga.model.IncomingEventType;
import com.github.marceloleite2604.pitanga.model.OutgoingEvent;

import java.util.Objects;

public abstract class AbstractEventHandler implements EventHandler {

    private final IncomingEventType incomingEventType;
    private EventHandler next;

    protected AbstractEventHandler(IncomingEventType incomingEventType) {
        this.incomingEventType = incomingEventType;
    }

    @Override
    public OutgoingEvent<?> handle(IncomingEvent<?> incomingEvent) {
        if (shouldHandle(incomingEvent)) {
            return doHandle(incomingEvent);
        } else {
            if (Objects.nonNull(next)) {
                return next.handle(incomingEvent);
            }
        }
        throw new IllegalArgumentException(String.format("Incoming event of type \"%s\" cannot be handled.", incomingEvent.getType()));
    }

    private boolean shouldHandle(IncomingEvent<?> incomingEvent) {
        return incomingEventType.equals(incomingEvent.getType());
    }

    protected abstract OutgoingEvent<?> doHandle(IncomingEvent<?> incomingEvent);

    @Override
    public void setNext(EventHandler next) {
        this.next = next;
    }
}
