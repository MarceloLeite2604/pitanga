package com.github.marceloleite2604.pitanga.dto.event.attendeevoted;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class AttendeeVotedEvent extends Event<AttendeeVotedPayload> {

    public AttendeeVotedEvent() {
        this(null);
    }

    @Builder
    public AttendeeVotedEvent(AttendeeVotedPayload payload) {
        super(EventType.ATTENDEE_VOTED, payload);
    }
}
