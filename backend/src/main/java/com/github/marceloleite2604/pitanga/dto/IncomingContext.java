package com.github.marceloleite2604.pitanga.dto;

import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.Payload;
import lombok.Builder;

public record IncomingContext(
        Event<? extends Payload> event,
        UserDto sender) {

    @Builder
    public IncomingContext{}
}
