package com.github.marceloleite2604.pitanga.dto.event.joinuser;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class JoinUserEvent extends Event<JoinUserPayload> {

    public JoinUserEvent() {
        this(null);
    }

    @Builder
    public JoinUserEvent(JoinUserPayload payload) {
        super(EventType.JOIN_USER, payload);
    }
}
