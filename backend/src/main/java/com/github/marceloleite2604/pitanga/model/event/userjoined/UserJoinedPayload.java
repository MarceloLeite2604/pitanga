package com.github.marceloleite2604.pitanga.model.event.userjoined;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import lombok.Builder;

@Builder
public class UserJoinedPayload {

    private final User user;

    private final Room room;
}
