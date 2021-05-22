package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;

public record CreateUserResult(Status status,
                               User user) {

    @Builder
    public CreateUserResult {}

    public enum Status {
        CREATED,
        MAX_USERS_REACHED
    }
}
