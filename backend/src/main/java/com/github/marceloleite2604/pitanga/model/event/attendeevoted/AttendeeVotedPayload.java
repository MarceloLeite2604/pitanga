package com.github.marceloleite2604.pitanga.model.event.attendeevoted;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.model.dao.AttendeeDao;
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
public class AttendeeVotedPayload implements Payload {

    private final AttendeeDao attendee;
}
