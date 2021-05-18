package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinUserResult {

    private final Status status;

    private final User user;

    public enum Status {
        USER_JOINED,
        MAX_ROOM_USERS_REACHED
    }
}
