package com.github.marceloleite2604.pitanga.dto.event.attendeevoted;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.dto.event.Payload;
import com.github.marceloleite2604.pitanga.model.VotingStatus;
import com.github.marceloleite2604.pitanga.dto.AttendeeDto;
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
public class AttendeeVotedPayload implements Payload {

    private final AttendeeDto attendee;

    private final VotingStatus votingStatus;
}
