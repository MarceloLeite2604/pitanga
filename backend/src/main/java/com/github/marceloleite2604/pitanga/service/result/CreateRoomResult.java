package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.Room;
import lombok.Builder;

public record CreateRoomResult(Status status,
                               Room room) {

    @Builder
    public CreateRoomResult{}

    public enum Status {
        CREATED,
        MAX_ROOMS_REACHED
    }
}
