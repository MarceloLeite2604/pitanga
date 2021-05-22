package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinUserResult {

    private final Status status;

    private final Attendee attendee;

    public enum Status {
        USER_JOINED,
        ALREADY_IN_ROOM,
        MAX_ROOM_USERS_REACHED
    }
}
