package com.github.marceloleite2604.pitanga.dto;

import com.github.marceloleite2604.pitanga.model.VotingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoomDto {

    @EqualsAndHashCode.Include
    private long id;

    @Singular
    private Set<AttendeeDto> attendees;

    private VotingStatus votingStatus;
}
