package com.github.marceloleite2604.pitanga.model.incoming;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncomingContext {

    private final IncomingEvent<?> incomingEvent;
    private final String sessionId;
}
