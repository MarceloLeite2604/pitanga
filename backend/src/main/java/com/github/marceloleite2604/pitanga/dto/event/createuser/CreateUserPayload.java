package com.github.marceloleite2604.pitanga.dto.event.createuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.dto.UserDto;
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
public class CreateUserPayload implements Payload {

    private final UserDto user;
}
