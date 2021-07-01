package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import com.github.marceloleite2604.pitanga.dto.AttendeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class AttendeeToDtoMapper extends AbstractMapper<Attendee, AttendeeDto> {

    private final UserToDtoMapper userToDtoMapper;

    private final VoteToDtoMapper voteToDtoMapper;

    @Override
    public AttendeeDto doMapTo(Attendee attendee) {
        return AttendeeDto.builder()
                .user(userToDtoMapper.mapTo(attendee.getUser()))
                .vote(voteToDtoMapper.mapTo(attendee.getVote()))
                .icon(attendee.getIcon())
                .joinedAt(attendee.getJoinedAt()
                        .toEpochSecond(ZoneOffset.UTC))
                .roomOwner(attendee.isRoomOwner())
                .build();
    }

    @Override
    public Attendee doMapFrom(AttendeeDto attendeeDto) {

        var user = userToDtoMapper.mapFrom(attendeeDto.getUser());

        var vote = voteToDtoMapper.mapFrom(attendeeDto.getVote());

        var id = AttendeeId.builder()
                .userId(user.getId())
                .build();

        return Attendee.builder()
                .id(id)
                .icon(attendeeDto.getIcon())
                .user(user)
                .vote(vote)
                .build();
    }
}
