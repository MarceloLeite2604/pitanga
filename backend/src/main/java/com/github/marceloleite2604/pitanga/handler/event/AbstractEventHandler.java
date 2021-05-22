package com.github.marceloleite2604.pitanga.handler.event;

import com.github.marceloleite2604.pitanga.model.IncomingContext;
import com.github.marceloleite2604.pitanga.model.OutgoingContext;
import com.github.marceloleite2604.pitanga.model.event.EventType;
import com.github.marceloleite2604.pitanga.model.event.Payload;
import com.github.marceloleite2604.pitanga.service.PitangaService;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractEventHandler<T extends Payload> implements EventHandler {

    protected final PitangaService pitangaService;
    private final EventType eventType;
    private EventHandler next;

    protected AbstractEventHandler(PitangaService pitangaService, EventType eventType) {
        this.pitangaService = pitangaService;
        this.eventType = eventType;
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
        throw new IllegalArgumentException(String.format("Incoming event of type \"%s\" cannot be handled.", incomingContext.event()
                .getType()));
    }

    private OutgoingContext mergeSessions(IncomingContext incomingContext, OutgoingContext outgoingContext) {
        Optional.ofNullable(incomingContext.sender())
                .ifPresent(sender -> outgoingContext.getRecipients()
                        .add(sender)
                );
        return outgoingContext;
    }

    private boolean shouldHandle(IncomingContext incomingContext) {
        return eventType.equals(incomingContext.event()
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

        if (Objects.isNull(eventType.getPayloadClass())) {
            var message = String.format("Event \"%s\" does not have a payload class type defined.", eventType.getValue());
            throw new IllegalStateException(message);
        }

        Object payload = incomingContext.event()
                .getPayload();

        if (Objects.isNull(payload)) {
            throw new IllegalArgumentException(
                    String.format("Incoming event \"%s\" does not have a payload.", eventType.getValue()));
        }

        if (!eventType.getPayloadClass()
                .isInstance(payload)) {
            throw new IllegalArgumentException(
                    String.format("Event \"%s\" must contain a payload of type \"%s\", but incoming event payload is of type \"%s\".",
                            eventType.getValue(),
                            eventType.getPayloadClass(),
                            payload.getClass()));
        }

        return (T) payload;
    }
}
