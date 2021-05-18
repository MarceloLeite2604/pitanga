package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResult {

    private final Status status;

    private final User user;

    public enum Status {
        CREATED,
        MAX_USERS_REACHED
    }
}
