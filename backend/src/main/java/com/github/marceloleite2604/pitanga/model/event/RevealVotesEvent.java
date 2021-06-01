package com.github.marceloleite2604.pitanga.model.event;

import lombok.Builder;

public class RevealVotesEvent extends Event<EmptyPayload> {

    @Builder
    public RevealVotesEvent() {
        super(EventType.REVEAL_VOTES, null);
    }
}
