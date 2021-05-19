package com.github.marceloleite2604.pitanga.model.event.userjoined;

import com.github.marceloleite2604.pitanga.model.dao.RoomDao;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserJoinedPayload {

    private final UserDao user;

    private final RoomDao room;
}
