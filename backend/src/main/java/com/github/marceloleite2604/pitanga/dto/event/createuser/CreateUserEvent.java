package com.github.marceloleite2604.pitanga.dto.event.createuser;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class CreateUserEvent extends Event<CreateUserPayload> {

    public CreateUserEvent() {
        this(null);
    }

    @Builder
    public CreateUserEvent(CreateUserPayload payload) {
        super(EventType.CREATE_USER, payload);
    }
}
