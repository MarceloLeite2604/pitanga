package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.marceloleite2604.pitanga.model.deserializer.IncomingEventDeserializer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(using = IncomingEventDeserializer.class)
public class IncomingEvent<T> {
    private final IncomingEventType type;

    private final T payload;
}
