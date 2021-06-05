package com.github.marceloleite2604.pitanga.dto.event.checkroomexists;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.dto.RoomDto;
import com.github.marceloleite2604.pitanga.dto.event.Payload;
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
public class CheckRoomExistsPayload implements Payload {

    private final RoomDto room;

    private final Boolean exists;
}
