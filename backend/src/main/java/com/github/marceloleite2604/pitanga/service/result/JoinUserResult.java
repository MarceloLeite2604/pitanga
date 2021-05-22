package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import lombok.Builder;

public record JoinUserResult(Status status,
                             Attendee attendee) {

    @Builder
    public JoinUserResult {}

    public enum Status {
        USER_JOINED,
        ALREADY_IN_ROOM,
        MAX_ROOM_USERS_REACHED
    }
}
