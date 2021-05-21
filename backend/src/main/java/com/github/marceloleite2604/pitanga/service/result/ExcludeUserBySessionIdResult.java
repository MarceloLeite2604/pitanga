package com.github.marceloleite2604.pitanga.service.result;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExcludeUserBySessionIdResult {

    private final User user;

    private final Room room;
}
