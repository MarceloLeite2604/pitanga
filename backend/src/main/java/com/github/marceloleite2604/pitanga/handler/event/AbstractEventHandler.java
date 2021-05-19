package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.service.PitangaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

public abstract class AbstractEventHandler<T> implements EventHandler {

    protected final PitangaService pitangaService;
    private final EventType eventType;
    private final Class<T> payloadClass;
    private EventHandler next;

    protected AbstractEventHandler(PitangaService pitangaService, EventType eventType, Class<T> payloadClass) {
        this.pitangaService = pitangaService;
        this.eventType = eventType;
        this.payloadClass = payloadClass;
    }

    @Override
    public OutgoingContext handle(IncomingContext incomingContext) {
        if (shouldHandle(incomingContext)) {
            var outgoingContext = doHandle(incomingContext);
            return mergeSessions(incomingContext, outgoingContext);
        } else {
            if (Objects.nonNull(next)) {
                return next.handle(incomingContext);
            }
        }
        throw new IllegalArgumentException(String.format("Incoming event of type \"%s\" cannot be handled.", incomingContext.getEvent()
                .getType()));
    }

    private OutgoingContext mergeSessions(IncomingContext incomingContext, OutgoingContext outgoingContext) {
        outgoingContext.getNotifiedSessions()
                .add(incomingContext.getSessionId());
        return outgoingContext;
    }

    private boolean shouldHandle(IncomingContext incomingContext) {
        return eventType.equals(incomingContext.getEvent()
                .getType());
    }

    @SuppressWarnings("squid:S1452")
    protected abstract OutgoingContext doHandle(IncomingContext incomingContext);

    @Override
    public void setNext(EventHandler next) {
        this.next = next;
    }

    @SuppressWarnings("unchecked")
    T retrievePayload(IncomingContext incomingContext) {
        Object payload = incomingContext.getEvent()
                .getPayload();
        if (Objects.isNull(payload)) {
            throw new IllegalArgumentException(
                    String.format("Incoming event \"%s\" does not have a payload.", eventType.getValue()));
        }

        if (!payloadClass
                .isInstance(payload)) {
            throw new IllegalArgumentException(
                    String.format("Event \"%s\" must contain a payload of type \"%s\", but incoming event payload is of type \"%s\".",
                            eventType.getValue(),
                            payloadClass,
                            payload.getClass()));
        }

        return (T) payload;
    }
}
