package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.Room;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateRoomResult {

    private final Status status;

    private final Room room;

    public enum Status {
        CREATED,
        MAX_ROOMS_REACHED
    }
}
