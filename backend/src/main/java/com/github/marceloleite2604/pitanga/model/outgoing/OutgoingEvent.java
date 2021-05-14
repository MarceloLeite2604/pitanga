package com.github.marceloleite2604.pitanga.model.outgoing;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.marceloleite2604.pitanga.model.deserializer.OutgoingEventSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
@JsonSerialize(using = OutgoingEventSerializer.class)
public class OutgoingEvent<T> {

    private final OutgoingEventType type;

    private final T payload;
}
