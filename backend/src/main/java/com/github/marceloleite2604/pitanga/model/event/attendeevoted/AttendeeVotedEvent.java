package com.github.marceloleite2604.pitanga.model.event.attendeevoted;

import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.EventType;
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
