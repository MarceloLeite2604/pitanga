package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.dto.AttendeeDto;
import com.github.marceloleite2604.pitanga.dto.RoomDto;
import com.github.marceloleite2604.pitanga.dto.UserDto;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AttendeeDtoToAttendeeIdMapper extends AbstractMapper<AttendeeDto, AttendeeId> {
    @Override
    protected AttendeeId doMapTo(AttendeeDto attendeeDto) {

        var userId = Optional.ofNullable(attendeeDto.getUser())
                .map(UserDto::getId)
                .orElse(null);

        var roomId = Optional.ofNullable(attendeeDto.getRoom())
                .map(RoomDto::getId)
                .orElse(null);

        return AttendeeId.builder()
                .userId(userId)
                .roomId(roomId)
                .build();
    }

    @Override
    protected AttendeeDto doMapFrom(AttendeeId attendeeId) {
        throw new UnsupportedOperationException("Cannot convert \"AttendeeId\" to \"AttendeeDto\".");
    }
}
