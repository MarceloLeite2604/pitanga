package com.github.marceloleite2604.pitanga.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.pitanga.handler.event.EventHandler;
import com.github.marceloleite2604.pitanga.dto.IncomingContext;
import com.github.marceloleite2604.pitanga.dto.OutgoingContext;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.dto.event.createuser.CreateUserEvent;
import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.createuser.CreateUserPayload;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedEvent;
import com.github.marceloleite2604.pitanga.dto.event.userdropped.UserDroppedPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class PitangaTextWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final EventHandler firstEventHandler;

    private final Map<String, WebSocketSession> sessions;

    public PitangaTextWebSocketHandler(ObjectMapper objectMapper, Set<EventHandler> eventHandlers) {
        this.objectMapper = objectMapper;
        this.firstEventHandler = createEventHandlerChain(eventHandlers);
        this.sessions = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.put(session.getId(), session);

        var user = UserDto.builder()
                .id(session.getId())
                .build();

        var createUserPayload = CreateUserPayload.builder()
                .user(user)
                .build();

        var createUserEvent = CreateUserEvent.builder()
                .payload(createUserPayload)
                .build();

        var incomingContext = IncomingContext.builder()
                .sender(user)
                .event(createUserEvent)
                .build();

        var outgoingContext = firstEventHandler.handle(incomingContext);

        sendEvent(outgoingContext);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session.getId());

        var user = UserDto.builder()
                .id(session.getId())
                .build();

        var userDroppedPayload = UserDroppedPayload.builder()
                .user(user)
                .build();

        var userDroppedEvent = UserDroppedEvent.builder()
                .payload(userDroppedPayload)
                .build();

        var incomingContext = IncomingContext.builder()
                .sender(user)
                .event(userDroppedEvent)
                .build();

        var outgoingContext = firstEventHandler.handle(incomingContext);

        sendEvent(outgoingContext);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage incomingTextMessage) {
        try {
            var incomingContext = createContext(session, incomingTextMessage);
            log.info("Received event \"{}\".", incomingContext.event()
                    .getType());
            var outgoingContext = firstEventHandler.handle(incomingContext);
            sendEvent(outgoingContext);
        } catch (Exception exception) {
            log.error("Error while handling message.", exception);
            throw exception;
        }
    }

    private void sendEvent(OutgoingContext outgoingContext) {

        if (CollectionUtils.isEmpty(outgoingContext.getRecipients())) {
            return;
        }

        var optionalOutgoingTextMessage = elaborateOutgoingTextMessage(outgoingContext);

        if (optionalOutgoingTextMessage.isEmpty()) {
            return;
        }

        var outgoingTextMessage = optionalOutgoingTextMessage.get();

        outgoingContext.getRecipients()
                .forEach(recipient ->
                        Optional.ofNullable(sessions.get(recipient.getId()))
                                .ifPresent(webSocketSession -> sendOutgoingTextMessage(webSocketSession, outgoingTextMessage)));
    }

    private IncomingContext createContext(WebSocketSession session, TextMessage incomingTextMessage) {
        var event = retrieveEvent(incomingTextMessage);

        var userDao = UserDto.builder()
                .id(session.getId())
                .build();

        return IncomingContext.builder()
                .event(event)
                .sender(userDao)
                .build();
    }

    private void sendOutgoingTextMessage(WebSocketSession session, TextMessage outgoingTextMessage) {
        try {
            session.sendMessage(outgoingTextMessage);
        } catch (IOException exception) {
            throw new IllegalStateException("Error while sending outgoing event.", exception);
        }
    }

    private Optional<TextMessage> elaborateOutgoingTextMessage(OutgoingContext outgoingContext) {
        if (Objects.isNull(outgoingContext.getEvent())) {
            return Optional.empty();
        }

        try {
            var payload = objectMapper.writeValueAsString(outgoingContext.getEvent());
            return Optional.of(new TextMessage(payload));
        } catch (JsonProcessingException exception) {
            var message = String.format("Error while creating payload for outgoing event \"%s\".", outgoingContext.getEvent()
                    .getType());
            throw new IllegalStateException(message, exception);
        }
    }

    private Event<?> retrieveEvent(TextMessage incomingTextMessage) {
        try {
            return objectMapper.readValue(incomingTextMessage.getPayload(), Event.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Received an event, but it could not be processed.", exception);
        }
    }

    private EventHandler createEventHandlerChain(Set<EventHandler> eventHandlerSet) {
        EventHandler first = null;
        EventHandler previous = null;
        for (EventHandler eventHandler : eventHandlerSet) {
            if (Objects.isNull(first)) {
                first = eventHandler;
            } else {
                previous.setNext(eventHandler);
            }
            previous = eventHandler;
        }
        return first;
    }
}
