package com.github.marceloleite2604.pitanga.model;

import com.github.marceloleite2604.pitanga.model.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OutgoingContext {

    @Builder.Default
    private final Set<String> notifiedSessions = new HashSet<>();

    private final Event<?> event;
}
