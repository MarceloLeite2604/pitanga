package com.github.marceloleite2604.pitanga.dto;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.Payload;
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
    private final Set<UserDto> recipients = new HashSet<>();

    private final Event<? extends Payload> event;
}
