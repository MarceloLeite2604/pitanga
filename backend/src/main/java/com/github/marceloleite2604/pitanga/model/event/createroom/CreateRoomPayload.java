package com.github.marceloleite2604.pitanga.model.event.createroom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.Payload;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomPayload implements Payload {

    private final UserDao user;
}
