package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingContext;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEventType;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEvent;

import java.util.Objects;

public abstract class AbstractEventHandler implements EventHandler {

    protected final PitangaService pitangaService;
    protected final IncomingEventType incomingEventType;
    private EventHandler next;

    protected AbstractEventHandler(PitangaService pitangaService, IncomingEventType incomingEventType) {
        this.pitangaService = pitangaService;
        this.incomingEventType = incomingEventType;
    }

    @Override
    public OutgoingEvent<?> handle(IncomingContext incomingContext) {
        if (shouldHandle(incomingContext)) {
            return doHandle(incomingContext);
        } else {
            if (Objects.nonNull(next)) {
                return next.handle(incomingContext);
            }
        }
        throw new IllegalArgumentException(String.format("Incoming event of type \"%s\" cannot be handled.", incomingContext.getIncomingEvent()
                .getType()));
    }

    private boolean shouldHandle(IncomingContext incomingContext) {
        return incomingEventType.equals(incomingContext.getIncomingEvent()
                .getType());
    }

    protected abstract OutgoingEvent<?> doHandle(IncomingContext incomingContext);

    @Override
    public void setNext(EventHandler next) {
        this.next = next;
    }

    @SuppressWarnings("unchecked")
    <T> T retrievePayload(IncomingContext incomingContext, Class<T> payloadClass) {
        Object payload = incomingContext.getIncomingEvent()
                .getPayload();
        if (Objects.isNull(payload)) {
            throw new IllegalArgumentException(
                    String.format("Incoming event \"%s\" does not have a payload.", incomingEventType.getValue()));
        }

        if (!payloadClass
                .isInstance(payload)) {
            throw new IllegalArgumentException(
                    String.format("Event \"%s\" must contain a payload of type \"%s\", but incoming event payload is of type \"%s\".",
                            incomingEventType.getValue(),
                            incomingEventType.getPayloadClass(),
                            payload.getClass()));
        }

        return (T) payload;
    }
}
