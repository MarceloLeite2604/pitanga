package com.github.marceloleite2604.pitanga.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.pitanga.PitangaService;
import com.github.marceloleite2604.pitanga.handler.event.EventHandler;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingContext;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEvent;
import com.github.marceloleite2604.pitanga.model.outgoing.OutgoingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class PitangaTextWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final EventHandler firstEventHandler;
    private final PitangaService pitangaService;

    private final Map<String, WebSocketSession> sessions;

    public PitangaTextWebSocketHandler(ObjectMapper objectMapper, Set<EventHandler> eventHandlerSet, PitangaService pitangaService) {
        this.objectMapper = objectMapper;
        this.firstEventHandler = createEventHandlerChain(eventHandlerSet);
        this.pitangaService = pitangaService;
        this.sessions = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        pitangaService.excludeUserBySessionId(session.getId());
        sessions.remove(session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage incomingTextMessage) {
        var incomingContext = createContext(session, incomingTextMessage);
        var outgoingEvent = firstEventHandler.handle(incomingContext);
        var outgoingTextMessage = elaborateOutgoingTextMessage(outgoingEvent);
        sendOutgoingTextMessage(session, outgoingTextMessage);
    }

    private IncomingContext createContext(WebSocketSession session, TextMessage incomingTextMessage) {
        IncomingEvent<?> incomingEvent = retrieveEvent(incomingTextMessage);
        return IncomingContext.builder()
                .incomingEvent(incomingEvent)
                .sessionId(session.getId())
                .build();
    }

    private void sendOutgoingTextMessage(WebSocketSession session, TextMessage outgoingTextMessage) {
        try {
            session.sendMessage(outgoingTextMessage);
        } catch (IOException exception) {
            throw new IllegalStateException("Error while sending outgoing event.", exception);
        }
    }

    private TextMessage elaborateOutgoingTextMessage(OutgoingEvent<?> outgoingEvent) {
        try {
            var payload = objectMapper.writeValueAsString(outgoingEvent);
            return new TextMessage(payload);
        } catch (JsonProcessingException exception) {
            var message = String.format("Error while creating payload for outgoing event \"%s\".", outgoingEvent.getType());
            throw new IllegalStateException(message, exception);
        }
    }

    private IncomingEvent<?> retrieveEvent(TextMessage incomingTextMessage) {
        try {
            return objectMapper.readValue(incomingTextMessage.getPayload(), IncomingEvent.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Received an event, but it could not be processed.", exception);
        }
    }

    private EventHandler createEventHandlerChain(Set<EventHandler> eventHandlerSet) {
        EventHandler first = null;
        EventHandler previous = null;
        for (EventHandler eventHandler : eventHandlerSet) {
            if (Objects.isNull(first)) {
                first = previous = eventHandler;
            } else {
                previous.setNext(eventHandler);
            }
        }
        return first;
    }
}
