package com.github.marceloleite2604.pitanga.dto.event.attendeeleftroom;

import com.github.marceloleite2604.pitanga.dto.event.Event;
import com.github.marceloleite2604.pitanga.dto.event.EventType;
import lombok.Builder;

public class AttendeeLeftRoomEvent extends Event<AttendeeLeftRoomPayload> {

    public AttendeeLeftRoomEvent() {
        this(null);
    }

    @Builder
    public AttendeeLeftRoomEvent(AttendeeLeftRoomPayload payload) {
        super(EventType.ATTENDEE_LEFT_ROOM, payload);
    }
}
