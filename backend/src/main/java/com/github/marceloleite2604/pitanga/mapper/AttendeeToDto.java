package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.model.attendee.AttendeeId;
import com.github.marceloleite2604.pitanga.dto.AttendeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class AttendeeToDto implements Mapper<Attendee, AttendeeDto> {

    private final UserToDto userToDto;

    private final VoteToDto voteToDto;

    @Override
    public AttendeeDto mapTo(Attendee attendee) {
        return AttendeeDto.builder()
                .user(userToDto.mapTo(attendee.getUser()))
                .vote(voteToDto.mapTo(attendee.getVote()))
                .icon(attendee.getIcon())
                .joinedAt(attendee.getJoinedAt()
                        .toEpochSecond(ZoneOffset.UTC))
                .roomOwner(attendee.isRoomOwner())
                .build();
    }

    @Override
    public Attendee mapFrom(AttendeeDto attendeeDto) {

        var user = userToDto.mapFrom(attendeeDto.getUser());

        var vote = voteToDto.mapFrom(attendeeDto.getVote());

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
