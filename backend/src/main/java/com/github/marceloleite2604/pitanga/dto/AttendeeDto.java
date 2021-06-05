package com.github.marceloleite2604.pitanga.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AttendeeDto {

    private UserDto user;

    private RoomDto room;

    private VoteDto vote;

    private String icon;

    private boolean roomOwner;

    private long joinedAt;
}
